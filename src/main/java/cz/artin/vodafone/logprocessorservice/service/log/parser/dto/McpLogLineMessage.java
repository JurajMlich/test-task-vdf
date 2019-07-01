package cz.artin.vodafone.logprocessorservice.service.log.parser.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class McpLogLineMessage extends McpLogLine {

    @NotNull
    private McpMessageStatus status;
    @NotNull
    private String messageContent;

    public McpLogLineMessage(
            LocalDateTime timestamp,
            McpCommunicationBetweenCountries between,
            McpMessageStatus status,
            String messageContent
    ) {
        super(timestamp, between);

        this.status = status;
        this.messageContent = messageContent;
    }

    public McpMessageStatus getStatus() {
        return status;
    }

    public void setStatus(McpMessageStatus status) {
        this.status = status;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
