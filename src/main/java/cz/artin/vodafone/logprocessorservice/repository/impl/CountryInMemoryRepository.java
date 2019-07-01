package cz.artin.vodafone.logprocessorservice.repository.impl;

import cz.artin.vodafone.logprocessorservice.model.Country;
import cz.artin.vodafone.logprocessorservice.repository.CountryRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CountryInMemoryRepository implements CountryRepository {

    private Set<Country> countries = new HashSet<>();

    @Override
    public Optional<Country> findByCallingCode(int callingCode) {
        return countries.stream()
                .filter(country -> country.getCallingCode().equals(callingCode))
                .findFirst();
    }

    @Override
    public Country save(Country country) {
        this.countries.add(country);

        return country;
    }

    @Override
    public long countAllByDestination(boolean isDestinationCountry) {
        return countries.stream()
                .filter(country -> country.isDestination() == isDestinationCountry)
                .count();
    }

    @Override
    public long countAllByOrigin(boolean isOriginCountry) {
        return countries.stream()
                .filter(country -> country.isOrigin() == isOriginCountry)
                .count();
    }
}
