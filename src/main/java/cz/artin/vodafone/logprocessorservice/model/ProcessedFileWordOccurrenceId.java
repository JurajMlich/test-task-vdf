package cz.artin.vodafone.logprocessorservice.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProcessedFileWordOccurrenceId implements Serializable {

    @Column(name = "word")
    private String word;

    @ManyToOne
    @JoinColumn(name = "processed_file_id")
    private ProcessedFile file;

    public ProcessedFileWordOccurrenceId() {
    }

    public ProcessedFileWordOccurrenceId(String word, ProcessedFile file) {
        this.word = word;
        this.file = file;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public ProcessedFile getFile() {
        return file;
    }

    public void setFile(ProcessedFile file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessedFileWordOccurrenceId that = (ProcessedFileWordOccurrenceId) o;
        return Objects.equals(word, that.word) &&
                Objects.equals(file, that.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, file);
    }
}
