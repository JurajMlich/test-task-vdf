package cz.artin.vodafone.logprocessorservice.service.log.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.artin.vodafone.logprocessorservice.service.log.analyzer.EmptyConstrainViolation;
import cz.artin.vodafone.logprocessorservice.service.log.parser.dto.McpCallStatusCode;
import cz.artin.vodafone.logprocessorservice.service.log.parser.dto.McpLogLineCall;
import cz.artin.vodafone.logprocessorservice.service.log.parser.exception.MissingPropertyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.Validator;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class LogParserTest {

    @Mock
    private Validator validator;
    @Mock
    private ObjectMapper objectMapper;
    private LogParser parser;

    @BeforeEach
    void createMocks() {
        MockitoAnnotations.initMocks(this);

        parser = new LogParser(objectMapper, validator);
    }

    @Test
    void parse() throws IOException {
        var testCall = new McpLogLineCall(null, null, 0, McpCallStatusCode.OK,
                null);
        var testCall2 = new McpLogLineCall(null, null, 0, McpCallStatusCode.OK,
                null);

        when(this.objectMapper.readValue(any(StringReader.class),
                any(Class.class)))
                .thenThrow(IOException.class)
                .thenThrow(MissingPropertyException.class)
                .thenReturn(testCall)
                .thenReturn(testCall2);

        when(this.validator.validate(testCall))
                .thenReturn(new HashSet<>());

        when(this.validator.validate(testCall2))
                .thenReturn(Set.of(new EmptyConstrainViolation<>()));

        var result = parser.parse(new String[]{"A", "B", "C", "D"});

        assertTrue(result.getItems().contains(testCall));
        assertEquals(1, result.getItems().size());
        assertEquals(2, result.getNumberOfRowsWithFieldErrors());
        assertEquals(1, result.getNumberOfRowsWithMissingFields());
    }
}