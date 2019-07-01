package cz.artin.vodafone.logprocessorservice.rest.v1.dto;

/**
 * @author Juraj Mlich <juraj.mlich@artin.cz>
 */
public class StatusDto {
    public String status;

    public StatusDto(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
