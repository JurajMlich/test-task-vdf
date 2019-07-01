package cz.artin.vodafone.logprocessorservice.service.log.analyzer;

import cz.artin.vodafone.logprocessorservice.service.log.parser.dto.McpCommunicationBetweenCountries;

import java.util.Map;

public class LogAnalyzerResult {

    private int numberOfCalls;
    private int numberOfMessages;
    private Map<McpCommunicationBetweenCountries, CallStats> callStatsBetweenCountries;
    private Map<String, Integer> wordOccurrenceInMessages;
    private int numberOfBlankMessages;
    private int numberOfOkCalls;
    private int numberOfKoCalls;

    LogAnalyzerResult(
            int numberOfCalls,
            int numberOfMessages,
            Map<McpCommunicationBetweenCountries, CallStats> callStatsBetweenCountries,
            Map<String, Integer> wordOccurrenceInMessages,
            int numberOfBlankMessages,
            int numberOfOkCalls,
            int numberOfKoCalls
    ) {
        this.numberOfCalls = numberOfCalls;
        this.numberOfMessages = numberOfMessages;
        this.callStatsBetweenCountries = callStatsBetweenCountries;
        this.wordOccurrenceInMessages = wordOccurrenceInMessages;
        this.numberOfBlankMessages = numberOfBlankMessages;
        this.numberOfOkCalls = numberOfOkCalls;
        this.numberOfKoCalls = numberOfKoCalls;
    }

    public int getNumberOfCalls() {
        return numberOfCalls;
    }

    public int getNumberOfMessages() {
        return numberOfMessages;
    }

    public Map<McpCommunicationBetweenCountries, CallStats> getCallStatsBetweenCountries() {
        return callStatsBetweenCountries;
    }

    public Map<String, Integer> getWordOccurrenceInMessages() {
        return wordOccurrenceInMessages;
    }

    public int getNumberOfBlankMessages() {
        return numberOfBlankMessages;
    }

    public int getNumberOfOkCalls() {
        return numberOfOkCalls;
    }

    public int getNumberOfKoCalls() {
        return numberOfKoCalls;
    }
}
