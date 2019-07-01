package cz.artin.vodafone.logprocessorservice.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "processed_log")
public class ProcessedLog {

    @Id
    @Column(name = "url")
    private LocalDate date;

    @Column(name = "number_of_rows")
    private int numberOfRows;
    @Column(name = "number_of_rows_with_missing_fields")
    private int numberOfRowsWithMissingFields;
    @Column(name = "number_of_rows_with_field_errors")
    private int numberOfRowsWithFieldErrors;
    @Column(name = "number_of_calls")
    private int numberOfCalls;
    @Column(name = "number_of_ok_calls")
    private int numberOfOkCalls;
    @Column(name = "number_of_ko_calls")
    private int numberOfKoCalls;
    @Column(name = "number_of_messages")
    private int numberOfMessages;
    @Column(name = "number_of_messages_with_blank_content")
    private int numberOfMessagesWithBlankContent;

    @Column(name = "process_duration")
    private long processDuration;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "id.log", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProcessedLogCountryCallStatistics> countryCallStatistics = new HashSet<>();
    @OneToMany(mappedBy = "id.log", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProcessedLogWordOccurrence> wordOccurrences = new HashSet<>();

    public ProcessedLog() {
    }

    public ProcessedLog(
            LocalDate date,
            int numberOfRows,
            int numberOfRowsWithMissingFields,
            int numberOfRowsWithFieldErrors,
            int numberOfCalls,
            int numberOfOkCalls,
            int numberOfKoCalls,
            int numberOfMessages,
            int numberOfMessagesWithBlankContent,
            long processDuration,
            LocalDateTime processedAt
    ) {
        this.date = date;
        this.numberOfRows = numberOfRows;
        this.numberOfRowsWithMissingFields = numberOfRowsWithMissingFields;
        this.numberOfRowsWithFieldErrors = numberOfRowsWithFieldErrors;
        this.numberOfCalls = numberOfCalls;
        this.numberOfOkCalls = numberOfOkCalls;
        this.numberOfKoCalls = numberOfKoCalls;
        this.numberOfMessages = numberOfMessages;
        this.numberOfMessagesWithBlankContent = numberOfMessagesWithBlankContent;
        this.processDuration = processDuration;
        this.processedAt = processedAt;
        this.active = false;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public int getNumberOfRowsWithMissingFields() {
        return numberOfRowsWithMissingFields;
    }

    public void setNumberOfRowsWithMissingFields(int numberOfRowsWithMissingFields) {
        this.numberOfRowsWithMissingFields = numberOfRowsWithMissingFields;
    }

    public int getNumberOfRowsWithFieldErrors() {
        return numberOfRowsWithFieldErrors;
    }

    public void setNumberOfRowsWithFieldErrors(int numberOfRowsWithFieldErrors) {
        this.numberOfRowsWithFieldErrors = numberOfRowsWithFieldErrors;
    }

    public int getNumberOfCalls() {
        return numberOfCalls;
    }

    public void setNumberOfCalls(int numberOfCalls) {
        this.numberOfCalls = numberOfCalls;
    }

    public int getNumberOfOkCalls() {
        return numberOfOkCalls;
    }

    public void setNumberOfOkCalls(int numberOfOkCalls) {
        this.numberOfOkCalls = numberOfOkCalls;
    }

    public int getNumberOfKoCalls() {
        return numberOfKoCalls;
    }

    public void setNumberOfKoCalls(int numberOfKoCalls) {
        this.numberOfKoCalls = numberOfKoCalls;
    }

    public int getNumberOfMessages() {
        return numberOfMessages;
    }

    public void setNumberOfMessages(int numberOfMessages) {
        this.numberOfMessages = numberOfMessages;
    }

    public int getNumberOfMessagesWithBlankContent() {
        return numberOfMessagesWithBlankContent;
    }

    public void setNumberOfMessagesWithBlankContent(int numberOfMessagesWithBlankContent) {
        this.numberOfMessagesWithBlankContent = numberOfMessagesWithBlankContent;
    }

    public long getProcessDuration() {
        return processDuration;
    }

    public void setProcessDuration(long processDuration) {
        this.processDuration = processDuration;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }

    public Set<ProcessedLogCountryCallStatistics> getCountryCallStatistics() {
        return countryCallStatistics;
    }

    public void setCountryCallStatistics(Set<ProcessedLogCountryCallStatistics> countryCallStatistics) {
        this.countryCallStatistics = countryCallStatistics;
    }

    public Set<ProcessedLogWordOccurrence> getWordOccurrences() {
        return wordOccurrences;
    }

    public void setWordOccurrences(Set<ProcessedLogWordOccurrence> wordOccurrences) {
        this.wordOccurrences = wordOccurrences;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessedLog that = (ProcessedLog) o;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
