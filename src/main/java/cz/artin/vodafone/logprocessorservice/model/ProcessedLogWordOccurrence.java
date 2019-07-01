package cz.artin.vodafone.logprocessorservice.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Represent information how many times a word was used in a message in a
 * {@link ProcessedLog}.
 *
 * @author Juraj Mlich <juraj.mlich@artin.cz>
 */
@Entity
@Table(name = "processed_log_word_occurrence")
public class ProcessedLogWordOccurrence {

    @EmbeddedId
    private ProcessedLogWordOccurrenceId id;

    @Column(name = "number_of_occurrences")
    private int numberOfOccurrences;

    public ProcessedLogWordOccurrence() {
    }

    public ProcessedLogWordOccurrence(ProcessedLogWordOccurrenceId id, int numberOfOccurrences) {
        this.id = id;
        this.numberOfOccurrences = numberOfOccurrences;
    }

    public ProcessedLogWordOccurrenceId getId() {
        return id;
    }

    public void setId(ProcessedLogWordOccurrenceId id) {
        this.id = id;
    }

    public int getNumberOfOccurrences() {
        return numberOfOccurrences;
    }

    public void setNumberOfOccurrences(int numberOfOccurrences) {
        this.numberOfOccurrences = numberOfOccurrences;
    }
}
