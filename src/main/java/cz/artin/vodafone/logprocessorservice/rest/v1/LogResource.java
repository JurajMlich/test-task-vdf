package cz.artin.vodafone.logprocessorservice.rest.v1;

import cz.artin.vodafone.logprocessorservice.model.ProcessedLog;
import cz.artin.vodafone.logprocessorservice.repository.ProcessedLogRepository;
import cz.artin.vodafone.logprocessorservice.rest.v1.dto.StatusDto;
import cz.artin.vodafone.logprocessorservice.service.log.LogService;
import cz.artin.vodafone.logprocessorservice.service.log.dto.LogMetricsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author Juraj Mlich <juraj.mlich@artin.cz>
 */
@RestController
@RequestMapping("logs")
public class LogResource {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final ProcessedLogRepository processedLogRepository;
    private final LogService logService;

    public LogResource(
            @Autowired ProcessedLogRepository processedLogRepository,
            @Autowired LogService logService
    ) {
        this.processedLogRepository = processedLogRepository;
        this.logService = logService;
    }

    /**
     * Get metrics for currently active {@link ProcessedLog}.
     *
     * @return metrics
     */
    @RequestMapping("active/metrics")
    public LogMetricsDto getMetrics() {
        var activeLog = this.processedLogRepository.findActive()
                .orElseThrow(IllegalStateException::new);

        return this.logService.buildMetrics(activeLog.getDate());
    }

    /**
     * Set active log.
     *
     * @param data date in format yyyyMMdd
     * @return status
     */
    @RequestMapping(path = "active", method = RequestMethod.PUT)
    public ResponseEntity<StatusDto> selectLog(
            @Valid @NotNull @RequestBody String data
    ) {
        try {
            var date = LocalDate.parse(data, DATE_FORMAT);

            this.logService.selectLog(date);
        } catch (IOException e) {
            return new ResponseEntity<>(
                    new StatusDto("ERROR - unable to download or parse a log file for the given date"),
                    HttpStatus.BAD_REQUEST
            );
        } catch (DateTimeParseException e) {
            return new ResponseEntity<>(
                    new StatusDto("ERROR - date in invalid format"),
                    HttpStatus.BAD_REQUEST
            );
        }

        return new ResponseEntity<>(
                new StatusDto("OK"),
                HttpStatus.OK
        );
    }


    // TODO remove, only for development purposes
    @RequestMapping(path = "active", method = RequestMethod.GET)
    public ResponseEntity<StatusDto> selectLog2(
            @Valid @NotNull @RequestParam(name = "date") String data
    ) {
        return selectLog(data);
    }
}
