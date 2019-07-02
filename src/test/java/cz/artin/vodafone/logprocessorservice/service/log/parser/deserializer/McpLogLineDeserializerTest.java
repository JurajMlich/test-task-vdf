package cz.artin.vodafone.logprocessorservice.service.log.parser.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.BaseJsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class McpLogLineDeserializerTest {

    @Mock
    JsonNode node;
    @Mock
    JsonNode node2;

    @Mock
    JsonParser jsonParser;
    @Mock
    ObjectCodec objectCodec;
    @Mock
    DeserializationContext deserializationContext;

    McpLogLineDeserializer deserializer;

    @BeforeEach
    void createMocks() throws IOException {
        MockitoAnnotations.initMocks(this);

        this.deserializer = new McpLogLineDeserializer();

        when(jsonParser.getCodec()).thenReturn(objectCodec);
        when(objectCodec.readTree(jsonParser)).thenReturn(node);
    }

    @Test
    void deserialize() throws IOException {
        var timestampNode = mock(JsonNode.class);
        var durationNode = mock(JsonNode.class);
        var statusDescriptionNode = mock(JsonNode.class);
        var statusCodeNode = mock(JsonNode.class);

        when(node.get("message_type")).thenReturn(node2);
        when(node.get("status_code")).thenReturn(statusCodeNode);
        when(node.get("timestamp")).thenReturn(timestampNode);
        when(node.get("duration")).thenReturn(durationNode);
        when(node.get("status_description")).thenReturn(statusDescriptionNode);
        when(node.has(any())).thenReturn(true);
        when(node2.textValue()).thenReturn("CALL");

        when(statusCodeNode.textValue()).thenReturn("OK");
        doReturn(true).when(timestampNode).isNumber();
        when(durationNode.isNumber()).thenReturn(true);
        LocalDateTime now = LocalDateTime.now();
        when(timestampNode.longValue()).thenReturn(Timestamp.valueOf(now).getTime());

        deserializer.deserialize(jsonParser,deserializationContext);


    }
}