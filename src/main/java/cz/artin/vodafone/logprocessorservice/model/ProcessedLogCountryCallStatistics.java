package cz.artin.vodafone.logprocessorservice.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Represent statistics of all calls for one processed log from one origin to
 * one destination.
 *
 * @author Juraj Mlich <juraj.mlich@artin.cz>
 */
@Entity
@Table(name = "processed_log_country_call_statistics")
public class ProcessedLogCountryCallStatistics {

    @EmbeddedId
    private ProcessedLogCountryCallStatisticsId id;

    @Column(name = "count_of_calls")
    private int numberOfCalls;

    /**
     * Average call duration in seconds.
     */
    @Column(name = "average_call_duration")
    private double averageCallDuration;

    public ProcessedLogCountryCallStatistics() {
    }

    public ProcessedLogCountryCallStatistics(
            ProcessedLogCountryCallStatisticsId id,
            int numberOfCalls,
            double averageCallDuration
    ) {
        this.id = id;
        this.numberOfCalls = numberOfCalls;
        this.averageCallDuration = averageCallDuration;
    }

    public ProcessedLogCountryCallStatisticsId getId() {
        return id;
    }

    public void setId(ProcessedLogCountryCallStatisticsId id) {
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
