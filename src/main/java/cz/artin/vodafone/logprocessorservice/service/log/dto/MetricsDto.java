package cz.artin.vodafone.logprocessorservice.service.log.dto;

import java.util.Map;
import java.util.Set;

public class MetricsDto {

    private int numberOfRowsWithMissingFields;
    private int numberOfMessagesWithBlankContent;
    private int numberOfRowsWithFieldsErrors;
    private Set<CountryCallStatisticsDto> callStatistics;
    private double ratioOkCallsToKo;
    private Map<String, Integer> wordOccurrences;

    public MetricsDto(int numberOfRowsWithMissingFields, int numberOfMessagesWithBlankContent, int numberOfRowsWithFieldsErrors, Set<CountryCallStatisticsDto> callStatistics, double ratioOkCallsToKo, Map<String, Integer> wordOccurrences) {
        this.numberOfRowsWithMissingFields = numberOfRowsWithMissingFields;
        this.numberOfMessagesWithBlankContent = numberOfMessagesWithBlankContent;
        this.numberOfRowsWithFieldsErrors = numberOfRowsWithFieldsErrors;
        this.callStatistics = callStatistics;
        this.ratioOkCallsToKo = ratioOkCallsToKo;
        this.wordOccurrences = wordOccurrences;
    }

    public int getNumberOfRowsWithMissingFields() {
        return numberOfRowsWithMissingFields;
    }

    public void setNumberOfRowsWithMissingFields(int numberOfRowsWithMissingFields) {
        this.numberOfRowsWithMissingFields = numberOfRowsWithMissingFields;
    }

    public int getNumberOfMessagesWithBlankContent() {
        return numberOfMessagesWithBlankContent;
    }

    public void setNumberOfMessagesWithBlankContent(int numberOfMessagesWithBlankContent) {
        this.numberOfMessagesWithBlankContent = numberOfMessagesWithBlankContent;
    }

    public int getNumberOfRowsWithFieldsErrors() {
        return numberOfRowsWithFieldsErrors;
    }

    public void setNumberOfRowsWithFieldsErrors(int numberOfRowsWithFieldsErrors) {
        this.numberOfRowsWithFieldsErrors = numberOfRowsWithFieldsErrors;
    }

    public Set<CountryCallStatisticsDto> getCallStatistics() {
        return callStatistics;
    }

    public void setCallStatistics(Set<CountryCallStatisticsDto> callStatistics) {
        this.callStatistics = callStatistics;
    }

    public double getRatioOkCallsToKo() {
        return ratioOkCallsToKo;
    }

    public void setRatioOkCallsToKo(double ratioOkCallsToKo) {
        this.ratioOkCallsToKo = ratioOkCallsToKo;
    }

    public Map<String, Integer> getWordOccurrences() {
        return wordOccurrences;
    }

    public void setWordOccurrences(Map<String, Integer> wordOccurrences) {
        this.wordOccurrences = wordOccurrences;
    }
}


