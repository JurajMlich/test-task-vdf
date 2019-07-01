package cz.artin.vodafone.logprocessorservice.service.log.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountryCallStatisticsDto {

    @JsonProperty("callFrom")
    private Integer callFromCountryCallingCode;
    @JsonProperty("callTo")
    private Integer callToCountryCallingCode;

    private int numberOfCalls;
    private double averageCallDuration;

    public CountryCallStatisticsDto(Integer callFromCountryCallingCode, Integer callToCountryCallingCode, int numberOfCalls, double averageCallDuration) {
        this.callFromCountryCallingCode = callFromCountryCallingCode;
        this.callToCountryCallingCode = callToCountryCallingCode;
        this.numberOfCalls = numberOfCalls;
        this.averageCallDuration = averageCallDuration;
    }

    public Integer getCallFromCountryCallingCode() {
        return callFromCountryCallingCode;
    }

    public void setCallFromCountryCallingCode(Integer callFromCountryCallingCode) {
        this.callFromCountryCallingCode = callFromCountryCallingCode;
    }

    public Integer getCallToCountryCallingCode() {
        return callToCountryCallingCode;
    }

    public void setCallToCountryCallingCode(Integer callToCountryCallingCode) {
        this.callToCountryCallingCode = callToCountryCallingCode;
    }

    public int getNumberOfCalls() {
        return numberOfCalls;
    }

    public void setNumberOfCalls(int numberOfCalls) {
        this.numberOfCalls = numberOfCalls;
    }

    public double getAverageCallDuration() {
        return averageCallDuration;
    }

    public void setAverageCallDuration(double averageCallDuration) {
        this.averageCallDuration = averageCallDuration;
    }
}

