package cz.artin.vodafone.logprocessorservice.dto;

import cz.artin.vodafone.logprocessorservice.model.Country;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class McpCommunicationBetweenCountries {
    @NotNull
    private Country origin;
    @NotNull
    private Country destination;

    public McpCommunicationBetweenCountries(Country origin, Country destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public Country getOrigin() {
        return origin;
    }

    public void setOrigin(Country origin) {
        this.origin = origin;
    }

    public Country getDestination() {
        return destination;
    }

    public void setDestination(Country destination) {
        this.destination = destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        McpCommunicationBetweenCountries that = (McpCommunicationBetweenCountries) o;
        return Objects.equals(origin, that.origin) &&
                Objects.equals(destination, that.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, destination);
    }
}
