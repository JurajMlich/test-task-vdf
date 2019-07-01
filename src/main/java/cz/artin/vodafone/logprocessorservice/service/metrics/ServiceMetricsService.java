package cz.artin.vodafone.logprocessorservice.service.metrics;

import cz.artin.vodafone.logprocessorservice.model.ProcessedFile;
import cz.artin.vodafone.logprocessorservice.repository.CountryRepository;
import cz.artin.vodafone.logprocessorservice.repository.ProcessedFileRepository;
import cz.artin.vodafone.logprocessorservice.service.metrics.dto.ServiceMetricsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ServiceMetricsService {

    private final ProcessedFileRepository processedFileRepository;
    private final CountryRepository countryRepository;

    public ServiceMetricsService(
            @Autowired ProcessedFileRepository processedFileRepository,
            @Autowired CountryRepository countryRepository
    ) {
        this.processedFileRepository = processedFileRepository;
        this.countryRepository = countryRepository;
    }

    public ServiceMetricsDto buildMetrics() {
        return new ServiceMetricsDto(
                this.processedFileRepository.countAll(),
                this.processedFileRepository.sumOfRows(),
                this.processedFileRepository.sumOfCalls(),
                this.processedFileRepository.sumOfMessages(),
                this.countryRepository.countAllByOrigin(true),
                this.countryRepository.countAllByDestination(true),
                // nicetodo:
                this.processedFileRepository.findAll().stream()
                        .collect(Collectors.toMap(ProcessedFile::getDate, ProcessedFile::getProcessDuration))
        );
    }
}
