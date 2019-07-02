package cz.artin.vodafone.logprocessorservice.service.log.analyzer;

import cz.artin.vodafone.logprocessorservice.service.log.parser.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogAnalyzerTest {

    private List<McpLogLine> testData;
    private LogAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        analyzer = new LogAnalyzer();
        testData = List.of(
                generateCall(McpCallStatusCode.OK, 10, 1, 2),
                generateCall(McpCallStatusCode.OK, 20, 2, 1),
                generateCall(McpCallStatusCode.KO, 30, 1, 2),
                generateMessage(McpMessageStatus.DELIVERED, "", 1, 1),
                generateMessage(McpMessageStatus.DELIVERED, LogAnalyzer.ANALYZE_WORDS[0] + " " + LogAnalyzer.ANALYZE_WORDS[0], 1, 1),
                generateMessage(McpMessageStatus.DELIVERED, "JOZKO A " + "MARIENKA", 1, 1)
        );
    }

    @Test
    void analyzeNumbers() {
        var result = analyzer.analyze(testData);
        assertEquals(3, result.getNumberOfCalls());
        assertEquals(2, result.getNumberOfOkCalls());
        assertEquals(1, result.getNumberOfKoCalls());
        assertEquals(1, result.getNumberOfBlankMessages());
        assertEquals(3, result.getNumberOfMessages());
    }

    @Test
    void analyzeWordOccurrence() {
        var result = analyzer.analyze(testData);
        assertEquals(2, result.getWordOccurrenceInMessages().get(LogAnalyzer.ANALYZE_WORDS[0]).intValue());
        assertEquals(0, result.getWordOccurrenceInMessages().get(LogAnalyzer.ANALYZE_WORDS[1]).intValue());
        assertEquals(LogAnalyzer.ANALYZE_WORDS.length, result.getWordOccurrenceInMessages().size());
    }

    @Test
    void analyzeCallStats() {
        var result = analyzer.analyze(testData);
        McpCommunicationBetweenCountries between =
                new McpCommunicationBetweenCountries(1, 2);
        assertEquals(2,
                result.getCallStatsBetweenCountries().get(between).getNumberOfCalls());
        assertEquals(20d,
                result.getCallStatsBetweenCountries().get(between).getAverageDuration());

        assertEquals(1,
                result.getCallStatsBetweenCountries().get(new McpCommunicationBetweenCountries(2, 1)).getNumberOfCalls());
        assertEquals(2, result.getCallStatsBetweenCountries().size());
    }

    McpLogLineCall generateCall(McpCallStatusCode status, int duration, int from, int to) {
        return new McpLogLineCall(
                null,
                new McpCommunicationBetweenCountries(from, to),
                duration,
                status, "");
    }

    McpLogLineMessage generateMessage(McpMessageStatus status, String content, int from, int to) {
        return new McpLogLineMessage(
                null,
                new McpCommunicationBetweenCountries(from, to),
                status,
                content);
    }
}