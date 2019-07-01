package cz.artin.vodafone.logprocessorservice.rest.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalyticsResource {
    // todo think about where these endpoints belong

    @RequestMapping("metrics")
    public Object getMetrics() {
        return null;
    }

    /**
     * The service will have an HTTP endpoint (/kpis) that returns a set of counters related with the service:
     *
     * Total number of processed JSON files
     * Total number of rows
     * Total number of calls
     * Total number of messages
     * Total number of different origin country codes (https://en.wikipedia.org/wiki/MSISDN)
     * Total number of different destination country codes (https://en.wikipedia.org/wiki/MSISDN)
     * Duration of each JSON process
     *
     * @return
     */
    @RequestMapping("kpis")
    public Object getServiceMetrics() {
        return null;
    }

}
