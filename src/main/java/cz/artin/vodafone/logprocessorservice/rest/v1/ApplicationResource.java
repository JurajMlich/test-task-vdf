package cz.artin.vodafone.logprocessorservice.rest.v1;

import cz.artin.vodafone.logprocessorservice.service.metrics.ApplicationMetricsService;
import cz.artin.vodafone.logprocessorservice.service.metrics.dto.ApplicationMetricsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Juraj Mlich <juraj.mlich@artin.cz>
 */
@RestController
public class ApplicationResource {

    private final ApplicationMetricsService applicationMetricsService;

    public ApplicationResource(
            @Autowired ApplicationMetricsService applicationMetricsService
    ) {
        this.applicationMetricsService = applicationMetricsService;
    }

    @RequestMapping("kpis")
    public ApplicationMetricsDto buildServiceMetrics() {
        // nicetodo: some kind of a caching mechanism so that there is no
        //  need rebuild them every time
        return this.applicationMetricsService.buildMetrics();
    }

}
