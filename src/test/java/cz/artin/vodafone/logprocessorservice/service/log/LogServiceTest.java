package cz.artin.vodafone.logprocessorservice.service.log;

import cz.artin.vodafone.logprocessorservice.model.ProcessedLog;
import cz.artin.vodafone.logprocessorservice.repository.CountryRepository;
import cz.artin.vodafone.logprocessorservice.repository.ProcessedLogRepository;
import cz.artin.vodafone.logprocessorservice.service.log.analyzer.LogAnalyzer;
import cz.artin.vodafone.logprocessorservice.service.log.downloader.LogDownloader;
import cz.artin.vodafone.logprocessorservice.service.log.parser.LogParser;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LogService.class})
class LogServiceTest {

    @Autowired
    private LogService logService;

    @MockBean
    private ProcessedLogRepository processedLogRepository;
    @MockBean
    private CountryRepository countryRepository;
    @MockBean
    private LogDownloader logDownloader;
    @MockBean
    private LogParser logParser;
    @MockBean
    private LogAnalyzer logAnalyzer;

    @Test
    void selectLog() {
        var date = LocalDate.of(2018,1,31);

        try {
            Mockito.when(processedLogRepository.findById(date)).thenReturn(Optional.of(new ProcessedLog()));

            logService.selectLog(date);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void buildMetrics() {
    }
}