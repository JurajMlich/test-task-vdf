package cz.artin.vodafone.logprocessorservice;

import cz.artin.vodafone.logprocessorservice.service.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;

@SpringBootApplication
public class Application {

    private final LogService logService;

    public Application(@Autowired LogService logService) {
        this.logService = logService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void init() {
        try {
            this.logService.process(LocalDate.of(2018, 1, 31));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
