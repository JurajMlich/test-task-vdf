package cz.artin.vodafone.logprocessorservice.repository.jpa;

import cz.artin.vodafone.logprocessorservice.model.Country;
import cz.artin.vodafone.logprocessorservice.repository.CountryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Juraj Mlich <juraj.mlich@artin.cz>
 */
@Repository
interface CountryJpaRepository extends JpaRepository<Country, Integer>, CountryRepository {

}
