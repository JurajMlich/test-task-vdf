package cz.artin.vodafone.logprocessorservice.rest.v1;

import cz.artin.vodafone.logprocessorservice.service.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("messages")
public class LogResource {

    private final LogService logService;

    public LogResource(
            @Autowired LogService logService
    ) {
        this.logService = logService;
    }

    @RequestMapping(path = "select", method = RequestMethod.GET)
    public String selectLogFile(
            @DateTimeFormat(pattern = "yyyyMMdd") @RequestParam(name = "date") LocalDate date
    ) {
        try {
            this.logService.process(date);
        } catch (IOException e) {
            return "ERROR - " + e.getMessage();
        }

        return "OK";
    }
}
