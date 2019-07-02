package cz.artin.vodafone.logprocessorservice.service.log.parser.deserializer;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import cz.artin.vodafone.logprocessorservice.service.log.parser.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ObjectMapper.class})
class McpLogLineDeserializerTest {

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void prepare() {
        var module = new SimpleModule("log-deserializer", Version.unknownVersion());
        module.addDeserializer(McpLogLine.class, new McpLogLineDeserializer());
        this.objectMapper.registerModule(module);
    }

    @Test
    void deserializeCall() throws IOException {

        var date = LocalDateTime.now().withNano(0);

        String content =
                "{\"message_type\": \"CALL\",\"timestamp\": " +
                        Math.floor(Timestamp.valueOf(date).getTime() / 1000) + "," +
                        "\"origin\": 34969000001,\"destination\": " +
                        "34969000101,\"duration\": 120,\"status_code\": " +
                        "\"OK\",\"status_description\": \"OK\"}";

        var obj = this.objectMapper.readValue(content, McpLogLine.class);

        assertEquals(date, obj.getTimestamp());
        assertEquals(34, obj.getBetween().getOriginCountryCallingCode().intValue());
        assertEquals(34, obj.getBetween().getDestinationCountryCallingCode().intValue());

        assertTrue(obj instanceof McpLogLineCall);

        var call = (McpLogLineCall) obj;

        assertEquals(120, call.getDuration());
        assertEquals(McpCallStatusCode.OK, call.getStatus());
        assertEquals("OK", call.getStatusDescription());
    }

    @Test
    void deserializeMessage() throws IOException {

        var date = LocalDateTime.now().withNano(0);

        String content =
                "{\"message_type\": \"MSG\",\"timestamp\": " +
                        Math.floor(Timestamp.valueOf(date).getTime() / 1000) + "," +
                        "\"origin\": 447005963437,\"destination\": " +
                        "447005963734,\"message_content\": \"LUNCH\",  " +
                        "\"message_status\": \"SEEN\"}";

        var obj = this.objectMapper.readValue(content, McpLogLine.class);

        assertEquals(date, obj.getTimestamp());
        assertEquals(44,
                obj.getBetween().getOriginCountryCallingCode().intValue());
        assertEquals(44,
                obj.getBetween().getDestinationCountryCallingCode().intValue());

        assertTrue(obj instanceof McpLogLineMessage);

        var message = (McpLogLineMessage) obj;

        assertEquals("LUNCH", message.getMessageContent());
        assertEquals(McpMessageStatus.SEEN, message.getStatus());
    }

    // nicetodo: tests for validation / errors
}