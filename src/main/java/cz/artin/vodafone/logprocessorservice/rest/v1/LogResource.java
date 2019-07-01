package cz.artin.vodafone.logprocessorservice.rest.v1;

import cz.artin.vodafone.logprocessorservice.repository.ProcessedLogRepository;
import cz.artin.vodafone.logprocessorservice.service.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("logs")
public class LogResource {

    private final ProcessedLogRepository processedLogRepository;
    private final LogService logService;

    public LogResource(
            @Autowired ProcessedLogRepository processedLogRepository,
            @Autowired LogService logService
    ) {
        this.processedLogRepository = processedLogRepository;
        this.logService = logService;
    }

    @RequestMapping("active/metrics")
    public Object getMetrics() {
        var activeLog = this.processedLogRepository.findActive()
                .orElseThrow(IllegalStateException::new);

        return this.logService.buildMetrics(activeLog.getDate());
    }

    // todo: PUT, @RequestBody
    @RequestMapping(path = "active", method = RequestMethod.GET)
    public String selectLog(
            @Valid @NotNull @DateTimeFormat(pattern = "yyyyMMdd") @RequestParam(name = "date") LocalDate date
    ) {
        try {
            this.logService.selectLog(date);
        } catch (IOException e) {
            return "ERROR - " + e.getMessage();
        }

        return "OK";
    }
}
