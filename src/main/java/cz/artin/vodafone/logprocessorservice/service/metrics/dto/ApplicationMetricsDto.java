package cz.artin.vodafone.logprocessorservice.service.metrics.dto;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author Juraj Mlich <juraj.mlich@artin.cz>
 */
public class ApplicationMetricsDto {

    private int numberOfProcessedJsonFiles;
    private int numberOfRows;
    private int numberOfCalls;
    private int numberOfMessages;
    private long numberOfDifferentOriginCountryCodes;
    private long numberOfDifferentDestinationCountryCodes;
    private Map<LocalDate, Long> durationOfEachJsonProcess;

    public ApplicationMetricsDto(int numberOfProcessedJsonFiles, int numberOfRows, int numberOfCalls, int numberOfMessages, long numberOfDifferentOriginCountryCodes, long numberOfDifferentDestinationCountryCodes, Map<LocalDate, Long> durationOfEachJsonProcess) {
        this.numberOfProcessedJsonFiles = numberOfProcessedJsonFiles;
        this.numberOfRows = numberOfRows;
        this.numberOfCalls = numberOfCalls;
        this.numberOfMessages = numberOfMessages;
        this.numberOfDifferentOriginCountryCodes = numberOfDifferentOriginCountryCodes;
        this.numberOfDifferentDestinationCountryCodes = numberOfDifferentDestinationCountryCodes;
        this.durationOfEachJsonProcess = durationOfEachJsonProcess;
    }

    public int getNumberOfProcessedJsonFiles() {
        return numberOfProcessedJsonFiles;
    }

    public void setNumberOfProcessedJsonFiles(int numberOfProcessedJsonFiles) {
        this.numberOfProcessedJsonFiles = numberOfProcessedJsonFiles;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public int getNumberOfCalls() {
        return numberOfCalls;
    }

    public void setNumberOfCalls(int numberOfCalls) {
        this.numberOfCalls = numberOfCalls;
    }

    public int getNumberOfMessages() {
        return numberOfMessages;
    }

    public void setNumberOfMessages(int numberOfMessages) {
        this.numberOfMessages = numberOfMessages;
    }

    public long getNumberOfDifferentOriginCountryCodes() {
        return numberOfDifferentOriginCountryCodes;
    }

    public void setNumberOfDifferentOriginCountryCodes(long numberOfDifferentOriginCountryCodes) {
        this.numberOfDifferentOriginCountryCodes = numberOfDifferentOriginCountryCodes;
    }

    public long getNumberOfDifferentDestinationCountryCodes() {
        return numberOfDifferentDestinationCountryCodes;
    }

    public void setNumberOfDifferentDestinationCountryCodes(long numberOfDifferentDestinationCountryCodes) {
        this.numberOfDifferentDestinationCountryCodes = numberOfDifferentDestinationCountryCodes;
    }

    public Map<LocalDate, Long> getDurationOfEachJsonProcess() {
        return durationOfEachJsonProcess;
    }

    public void setDurationOfEachJsonProcess(Map<LocalDate, Long> durationOfEachJsonProcess) {
        this.durationOfEachJsonProcess = durationOfEachJsonProcess;
    }
}
