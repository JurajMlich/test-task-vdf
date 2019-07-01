package cz.artin.vodafone.logprocessorservice.repository;

import cz.artin.vodafone.logprocessorservice.model.Country;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Storage for {@link Country}.
 *
 * @author Juraj Mlich <juraj.mlich@artin.cz>
 */
@Repository
public interface CountryRepository {

    Optional<Country> findByCallingCode(int callingCode);

    Country save(Country country);

    long countAllByDestination(boolean isDestinationCountry);

    long countAllByOrigin(boolean isOriginCountry);
}
