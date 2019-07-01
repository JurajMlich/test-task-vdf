package cz.artin.vodafone.logprocessorservice.config;

import cz.artin.vodafone.logprocessorservice.repository.CountryRepository;
import cz.artin.vodafone.logprocessorservice.repository.ProcessedLogRepository;
import cz.artin.vodafone.logprocessorservice.repository.impl.CountryInMemoryRepository;
import cz.artin.vodafone.logprocessorservice.repository.impl.ProcessedLogInMemoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class used for swapping JPA repositories with in memory
 * repositories.
 *
 * @author Juraj Mlich <juraj.mlich@artin.cz>
 */
@Configuration
public class RepositoryConfig {

    @Bean
    public CountryRepository countryRepository() {
        return new CountryInMemoryRepository();
    }

    @Bean
    public ProcessedLogRepository processedLogRepository() {
        return new ProcessedLogInMemoryRepository();
    }
}
