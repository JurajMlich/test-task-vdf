package cz.artin.vodafone.logprocessorservice.service.log.parser.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author Juraj Mlich <juraj.mlich@artin.cz>
 */
public class McpLogLineCall extends McpLogLine {

    @Min(1)
    private int duration;
    @NotNull
    private McpCallStatusCode status;
    @NotNull
    private String statusDescription;

    public McpLogLineCall(
            LocalDateTime timestamp,
            McpCommunicationBetweenCountries between,
            int duration,
            McpCallStatusCode status,
            String statusDescription
    ) {
        super(timestamp, between);
        this.duration = duration;
        this.status = status;
        this.statusDescription = statusDescription;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public McpCallStatusCode getStatus() {
        return status;
    }

    public void setStatus(McpCallStatusCode status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}
