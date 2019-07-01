package cz.artin.vodafone.logprocessorservice.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "processed_file_word_occurrence")
public class ProcessedFileWordOccurrence {
    @EmbeddedId
    private ProcessedFileWordOccurrenceId id;

    @Column(name = "number_of_occurrences")
    private int numberOfOccurrences;

    public ProcessedFileWordOccurrence() {
    }

    public ProcessedFileWordOccurrence(ProcessedFileWordOccurrenceId id, int numberOfOccurrences) {
        this.id = id;
        this.numberOfOccurrences = numberOfOccurrences;
    }

    public ProcessedFileWordOccurrenceId getId() {
        return id;
    }

    public void setId(ProcessedFileWordOccurrenceId id) {
        this.id = id;
    }

    public int getNumberOfOccurrences() {
        return numberOfOccurrences;
    }

    public void setNumberOfOccurrences(int numberOfOccurrences) {
        this.numberOfOccurrences = numberOfOccurrences;
    }
}
