package cz.artin.vodafone.logprocessorservice.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Juraj Mlich <juraj.mlich@artin.cz>
 */
@Embeddable
public class ProcessedLogWordOccurrenceId implements Serializable {

    @Column(name = "word")
    private String word;

    @ManyToOne
    @JoinColumn(name = "processed_log_id")
    private ProcessedLog log;

    public ProcessedLogWordOccurrenceId() {
    }

    public ProcessedLogWordOccurrenceId(String word, ProcessedLog log) {
        this.word = word;
        this.log = log;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public ProcessedLog getLog() {
        return log;
    }

    public void setLog(ProcessedLog log) {
        this.log = log;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessedLogWordOccurrenceId that = (ProcessedLogWordOccurrenceId) o;
        return Objects.equals(word, that.word) &&
                Objects.equals(log, that.log);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, log);
    }
}
