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

    static final String FIELD_MESSAGE_TYPE = "message_type";
    static final String FIELD_TIMESTAMP = "timestamp";
    static final String FIELD_ORIGIN = "origin";
    static final String FIELD_DESTINATION = "destination";
    static final String FIELD_MESSAGE_STATUS = "message_status";
    static final String FIELD_MESSAGE_CONTENT = "message_content";
    static final String FIELD_DURATION = "duration";
    static final String FIELD_STATUS_CODE = "status_code";
    static final String FIELD_STATUS_DESCRIPTION = "status_description";

    private final PhoneNumberUtil phoneNumberUtil;

    public McpLogLineDeserializer() {
        super(McpLogLine.class);

        this.phoneNumberUtil = PhoneNumberUtil.getInstance();
    }

    @Override
    public McpLogLine deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);

        ensureFieldsExist(node, FIELD_MESSAGE_TYPE, FIELD_TIMESTAMP, FIELD_ORIGIN, FIELD_DESTINATION);

        var type = node.get(FIELD_MESSAGE_TYPE).textValue();

        if ("CALL".equals(type)) {
            return parseCall(node);
        } else if ("MSG".equals(type)) {
            return parseMsg(node);
        } else {
            throw new ParseException("Unknown message type");
        }
    }

    private McpLogLineMessage parseMsg(JsonNode node) throws MissingPropertyException, FieldErrorException {
        ensureFieldsExist(node, FIELD_MESSAGE_STATUS, FIELD_MESSAGE_CONTENT);

        try {
            var status = McpMessageStatus.valueOf(node.get(FIELD_MESSAGE_STATUS).textValue());

            if (!node.get(FIELD_TIMESTAMP).isNumber()) {
                throw new FieldErrorException("Field " + FIELD_TIMESTAMP + " is in " +
                        "incorrect format.");
            }

            return new McpLogLineMessage(
                    dateTimeFromEpoch(node.get(FIELD_TIMESTAMP).longValue()),
                    parseBetween(node),
                    status,
                    node.get(FIELD_MESSAGE_CONTENT).textValue()
            );
        } catch (IllegalArgumentException e) {
            throw new FieldErrorException(e.getMessage());
        }
    }

    private McpLogLineCall parseCall(JsonNode node) throws MissingPropertyException, FieldErrorException {
        ensureFieldsExist(node, FIELD_DURATION, FIELD_STATUS_CODE, FIELD_STATUS_DESCRIPTION);

        var status = McpCallStatusCode.valueOf(node.get(FIELD_STATUS_CODE).textValue());

        try {
            if (!node.get(FIELD_TIMESTAMP).isNumber()) {
                throw new FieldErrorException("Field " + FIELD_TIMESTAMP + " is in " +
                        "incorrect format.");
            } else if (!node.get(FIELD_DURATION).isNumber()) {
                throw new FieldErrorException("Field " + FIELD_DURATION + " is in " +
                        "incorrect format.");
            }

            return new McpLogLineCall(
                    dateTimeFromEpoch(node.get(FIELD_TIMESTAMP).longValue()),
                    parseBetween(node),
                    node.get(FIELD_DURATION).intValue(),
                    status,
                    node.get(FIELD_STATUS_DESCRIPTION).textValue()
            );
        } catch (IllegalArgumentException e) {
            throw new FieldErrorException(e.getMessage());
        }
    }

    private McpCommunicationBetweenCountries parseBetween(JsonNode node) throws FieldErrorException {
        ensureFieldsAreNumber(node, FIELD_ORIGIN, FIELD_DESTINATION);

        try {
            return new McpCommunicationBetweenCountries(
                    parseCountryFromMSISDN(String.valueOf(node.get(FIELD_ORIGIN).longValue())),
                    parseCountryFromMSISDN(String.valueOf(node.get(FIELD_DESTINATION).longValue()))
            );
        } catch (NumberParseException e) {
            throw new FieldErrorException("MSISDN in incorrect format");
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
