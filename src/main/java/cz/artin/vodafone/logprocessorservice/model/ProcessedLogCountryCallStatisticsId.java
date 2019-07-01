package cz.artin.vodafone.logprocessorservice.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProcessedLogCountryCallStatisticsId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "processed_log_id")
    private ProcessedLog log;

    @ManyToOne
    @JoinColumn(name = "call_from_country_id")
    private Country callFrom;

    @ManyToOne
    @JoinColumn(name = "call_to_country_id")
    private Country callTo;

    public ProcessedLogCountryCallStatisticsId() {
    }

    public ProcessedLogCountryCallStatisticsId(
            ProcessedLog log,
            Country callFrom,
            Country callTo
    ) {
        this.log = log;
        this.callFrom = callFrom;
        this.callTo = callTo;
    }

    public ProcessedLog getLog() {
        return log;
    }

    public void setLog(ProcessedLog log) {
        this.log = log;
    }

    public Country getCallFrom() {
        return callFrom;
    }

    public void setCallFrom(Country callFrom) {
        this.callFrom = callFrom;
    }

    public Country getCallTo() {
        return callTo;
    }

    public void setCallTo(Country callTo) {
        this.callTo = callTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessedLogCountryCallStatisticsId that = (ProcessedLogCountryCallStatisticsId) o;
        return Objects.equals(log, that.log) &&
                Objects.equals(callFrom, that.callFrom) &&
                Objects.equals(callTo, that.callTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(log, callFrom, callTo);
    }
}
