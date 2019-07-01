package cz.artin.vodafone.logprocessorservice.service.log.parser.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author Juraj Mlich <juraj.mlich@artin.cz>
 */
public class McpCommunicationBetweenCountries {

    @NotNull
    private Integer originCountryCallingCode;
    @NotNull
    private Integer destinationCountryCallingCode;

    public McpCommunicationBetweenCountries(@NotNull Integer originCountryCallingCode, @NotNull Integer destinationCountryCallingCode) {
        this.originCountryCallingCode = originCountryCallingCode;
        this.destinationCountryCallingCode = destinationCountryCallingCode;
    }

    public Integer getOriginCountryCallingCode() {
        return originCountryCallingCode;
    }

    public void setOriginCountryCallingCode(Integer originCountryCallingCode) {
        this.originCountryCallingCode = originCountryCallingCode;
    }

    public Integer getDestinationCountryCallingCode() {
        return destinationCountryCallingCode;
    }

    public void setDestinationCountryCallingCode(Integer destinationCountryCallingCode) {
        this.destinationCountryCallingCode = destinationCountryCallingCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        McpCommunicationBetweenCountries that = (McpCommunicationBetweenCountries) o;
        return Objects.equals(originCountryCallingCode, that.originCountryCallingCode) &&
                Objects.equals(destinationCountryCallingCode, that.destinationCountryCallingCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originCountryCallingCode, destinationCountryCallingCode);
    }
}
