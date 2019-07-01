package cz.artin.vodafone.logprocessorservice.service.log.parser.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import cz.artin.vodafone.logprocessorservice.service.log.parser.dto.*;
import cz.artin.vodafone.logprocessorservice.service.log.parser.exception.FieldErrorException;
import cz.artin.vodafone.logprocessorservice.service.log.parser.exception.MissingPropertyException;
import cz.artin.vodafone.logprocessorservice.service.log.parser.exception.ParseException;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

/**
 * Jackson deserializer for type {@link McpLogLine} that produces two types of
 * objects {@link McpLogLineCall} or {@link McpLogLineMessage} depending on
 * {@code message_type} value.
 *
 * @author Juraj Mlich <juraj.mlich@artin.cz>
 */
public class McpLogLineDeserializer extends StdDeserializer<McpLogLine> {

    private final PhoneNumberUtil phoneNumberUtil;

    public McpLogLineDeserializer() {
        super(McpLogLine.class);

        this.phoneNumberUtil = PhoneNumberUtil.getInstance();
    }

    @Override
    public McpLogLine deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);

        ensureFieldsExist(node, "message_type", "timestamp", "origin", "destination");

        var type = node.get("message_type").textValue();

        if ("CALL".equals(type)) {
            return parseCall(node);
        } else if ("MSG".equals(type)) {
            return parseMsg(node);
        } else {
            throw new ParseException("Unknown message type");
        }
    }

    private McpLogLineMessage parseMsg(JsonNode node) throws MissingPropertyException, FieldErrorException {
        ensureFieldsExist(node, "message_status", "message_content");

        try {
            var status = McpMessageStatus.valueOf(node.get("message_status").textValue());

            if (!node.get("timestamp").isNumber()) {
                throw new IllegalStateException("Timestamp is in incorrect format.");
            }

            return new McpLogLineMessage(
                    dateTimeFromEpoch(node.get("timestamp").longValue()),
                    parseBetween(node),
                    status,
                    node.get("message_content").textValue()
            );
        } catch (IllegalArgumentException e) {
            throw new FieldErrorException(e.getMessage());
        }
    }

    private McpLogLineCall parseCall(JsonNode node) throws MissingPropertyException, FieldErrorException {
        ensureFieldsExist(node, "duration", "status_code", "status_description");

        var status = McpCallStatusCode.valueOf(node.get("status_code").textValue());

        try {
            if (!node.get("timestamp").isNumber()) {
                throw new FieldErrorException("Timestamp is in incorrect format.");
            } else if (!node.get("duration").isNumber()) {
                throw new FieldErrorException("Duration is in incorrect format.");
            }

            return new McpLogLineCall(
                    dateTimeFromEpoch(node.get("timestamp").longValue()),
                    parseBetween(node),
                    node.get("duration").intValue(),
                    status,
                    node.get("status_description").textValue()
            );
        } catch (IllegalArgumentException e) {
            throw new FieldErrorException(e.getMessage());
        }
    }

    private McpCommunicationBetweenCountries parseBetween(JsonNode node) throws FieldErrorException {
        ensureFieldsAreNumber(node, "origin", "destination");

        try {
            return new McpCommunicationBetweenCountries(
                    parseCountryFromMSISDN(String.valueOf(node.get("origin").intValue())),
                    parseCountryFromMSISDN(String.valueOf(node.get("destination").intValue()))
            );
        } catch (NumberParseException e) {
            throw new FieldErrorException("Phone number in incorrect format");
        }
    }

    // nicetodo: if the class was to grow any bigger, split it into several
    //  deserializers and move this common util methods in a special utility
    //  class

    private int parseCountryFromMSISDN(String msisdn) throws NumberParseException {
        if (!msisdn.startsWith("+")) {
            msisdn = "+" + msisdn;
        }

        return this.phoneNumberUtil.parse(msisdn, "").getCountryCode();
    }

    private LocalDateTime dateTimeFromEpoch(long epoch) {
        return LocalDateTime.ofInstant(
                Instant.ofEpochSecond(epoch),
                TimeZone.getDefault().toZoneId()
        );
    }

    private void ensureFieldsExist(JsonNode node, String... fields) throws MissingPropertyException {
        for (String field : fields) {
            if (!node.has(field)) {
                throw new MissingPropertyException("Field " + field + " not " +
                        "found." + node.toString());
            }
        }
    }

    private void ensureFieldsAreNumber(JsonNode node, String... fields) throws FieldErrorException {
        for (String field : fields) {
            if (!node.get(field).isNumber()) {
                throw new FieldErrorException("Field " + field + " not " +
                        "found." + node.toString());
            }
        }
    }

}
