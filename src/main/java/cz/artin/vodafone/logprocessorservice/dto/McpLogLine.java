package cz.artin.vodafone.logprocessorservice.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

public abstract class McpLogLine {
    @NotNull
    @PastOrPresent
    private LocalDateTime timestamp;
    @NotNull
    private McpCommunicationBetweenCountries between;

    public McpLogLine(LocalDateTime timestamp, McpCommunicationBetweenCountries between) {
        this.timestamp = timestamp;
        this.between = between;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public McpCommunicationBetweenCountries getBetween() {
        return between;
    }

    public void setBetween(McpCommunicationBetweenCountries between) {
        this.between = between;
    }
}
