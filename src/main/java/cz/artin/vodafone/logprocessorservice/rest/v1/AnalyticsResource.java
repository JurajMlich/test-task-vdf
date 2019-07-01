package cz.artin.vodafone.logprocessorservice.rest.v1;

import cz.artin.vodafone.logprocessorservice.repository.ProcessedFileRepository;
import cz.artin.vodafone.logprocessorservice.service.log.LogService;
import cz.artin.vodafone.logprocessorservice.service.metrics.ServiceMetricsService;
import cz.artin.vodafone.logprocessorservice.service.metrics.dto.ServiceMetricsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalyticsResource {

    private final ProcessedFileRepository processedFileRepository;
    private final ServiceMetricsService serviceMetricsService;
    private final LogService logService;

    public AnalyticsResource(
            @Autowired ProcessedFileRepository processedFileRepository,
            @Autowired ServiceMetricsService serviceMetricsService,
            @Autowired LogService logService
    ) {
        this.processedFileRepository = processedFileRepository;
        this.serviceMetricsService = serviceMetricsService;
        this.logService = logService;
    }

    @RequestMapping("metrics")
    public Object getMetrics() {
        var activeFile = this.processedFileRepository.findActive()
                .orElseThrow(IllegalStateException::new);

        return this.logService.buildMetrics(activeFile.getDate());
    }

    @RequestMapping("kpis")
    public ServiceMetricsDto getServiceMetrics() {
        return this.serviceMetricsService.buildMetrics();
    }

}
