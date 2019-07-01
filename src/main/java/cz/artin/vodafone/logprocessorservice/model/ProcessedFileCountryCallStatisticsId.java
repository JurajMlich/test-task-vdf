package cz.artin.vodafone.logprocessorservice.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProcessedFileCountryCallStatisticsId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "processed_file_id")
    private ProcessedFile file;

    @ManyToOne
    @JoinColumn(name = "call_from_country_id")
    private Country callFrom;

    @ManyToOne
    @JoinColumn(name = "call_to_country_id")
    private Country callTo;

    public ProcessedFileCountryCallStatisticsId() {
    }

    public ProcessedFileCountryCallStatisticsId(
            ProcessedFile file,
            Country callFrom,
            Country callTo
    ) {
        this.file = file;
        this.callFrom = callFrom;
        this.callTo = callTo;
    }

    public ProcessedFile getFile() {
        return file;
    }

    public void setFile(ProcessedFile file) {
        this.file = file;
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
        ProcessedFileCountryCallStatisticsId that = (ProcessedFileCountryCallStatisticsId) o;
        return Objects.equals(file, that.file) &&
                Objects.equals(callFrom, that.callFrom) &&
                Objects.equals(callTo, that.callTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, callFrom, callTo);
    }
}
