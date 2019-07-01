package cz.artin.vodafone.logprocessorservice.service.metrics;

import cz.artin.vodafone.logprocessorservice.model.ProcessedLog;
import cz.artin.vodafone.logprocessorservice.repository.CountryRepository;
import cz.artin.vodafone.logprocessorservice.repository.ProcessedLogRepository;
import cz.artin.vodafone.logprocessorservice.service.metrics.dto.ApplicationMetricsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ApplicationMetricsService {

    private final ProcessedLogRepository processedLogRepository;
    private final CountryRepository countryRepository;

    public ApplicationMetricsService(
            @Autowired ProcessedLogRepository processedLogRepository,
            @Autowired CountryRepository countryRepository
    ) {
        this.processedLogRepository = processedLogRepository;
        this.countryRepository = countryRepository;
    }

    public ApplicationMetricsDto buildMetrics() {
        return new ApplicationMetricsDto(
                this.processedLogRepository.countAll(),
                this.processedLogRepository.sumOfRows(),
                this.processedLogRepository.sumOfCalls(),
                this.processedLogRepository.sumOfMessages(),
                this.countryRepository.countAllByOrigin(true),
                this.countryRepository.countAllByDestination(true),
                // nicetodo:
                this.processedLogRepository.findAll().stream()
                        .collect(Collectors.toMap(ProcessedLog::getDate, ProcessedLog::getProcessDuration))
        );
    }
}
