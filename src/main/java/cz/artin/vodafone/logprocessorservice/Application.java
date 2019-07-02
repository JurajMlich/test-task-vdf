package cz.artin.vodafone.logprocessorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry to the Application.
 *
 * @author Juraj Mlich <juraj.mlich@artin.cz>
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // nicetodo: prepare default JSON error page
}
