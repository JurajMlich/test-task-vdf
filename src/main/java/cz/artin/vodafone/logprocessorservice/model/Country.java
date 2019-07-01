package cz.artin.vodafone.logprocessorservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * @author Juraj Mlich <juraj.mlich@artin.cz>
 */
@Entity
@Table(name = "country")
public class Country {

    /**
     * Country dial-in code as defined by the ITU-T in standards E.123 and E.164
     *
     * @see <a href="https://en.wikipedia.org/wiki/List_of_country_calling_codes">
     * Wikipedia page describing the calling code
     * </a>
     */
    @Id
    @Column(name = "calling_code")
    private Integer callingCode;

    /**
     * Was this country used as destination country in a call or message?
     */
    @Column(name = "destination")
    private boolean destination = false;

    /**
     * Was this country used as origin country in a call or message?
     */
    @Column(name = "origin")
    private boolean origin = false;

    public Country() {
    }

    public Country(Integer callingCode) {
        this.callingCode = callingCode;
    }

    public Integer getCallingCode() {
        return callingCode;
    }

    public void setCallingCode(Integer callingCode) {
        this.callingCode = callingCode;
    }

    public boolean isDestination() {
        return destination;
    }

    public void setDestination(boolean destination) {
        this.destination = destination;
    }

    public boolean isOrigin() {
        return origin;
    }

    public void setOrigin(boolean origin) {
        this.origin = origin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(callingCode, country.callingCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(callingCode);
    }
}
