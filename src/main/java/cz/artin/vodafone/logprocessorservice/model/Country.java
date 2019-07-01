package cz.artin.vodafone.logprocessorservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "country")
public class Country {
    @Id
    @Column(name = "calling_code")
    private Integer callingCode;

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
