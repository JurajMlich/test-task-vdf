package cz.artin.vodafone.logprocessorservice.repository;

import cz.artin.vodafone.logprocessorservice.model.Country;
import cz.artin.vodafone.logprocessorservice.model.ProcessedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
}
