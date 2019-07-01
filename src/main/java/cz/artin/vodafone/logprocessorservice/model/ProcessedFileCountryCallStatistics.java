package cz.artin.vodafone.logprocessorservice.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "processed_file_country_call_statistics")
public class ProcessedFileCountryCallStatistics {
    @EmbeddedId
    private ProcessedFileCountryCallStatisticsId id;

    @Column(name = "count_of_calls")
    private int numberOfCalls;
    @Column(name = "average_call_duration")
    private double averageCallDuration;

    public ProcessedFileCountryCallStatistics() {
    }

    public ProcessedFileCountryCallStatistics(
            ProcessedFileCountryCallStatisticsId id,
            int numberOfCalls,
            double averageCallDuration
    ) {
        this.id = id;
        this.numberOfCalls = numberOfCalls;
        this.averageCallDuration = averageCallDuration;
    }

    public ProcessedFileCountryCallStatisticsId getId() {
        return id;
    }

    public void setId(ProcessedFileCountryCallStatisticsId id) {
        this.id = id;
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
