package cz.artin.vodafone.logprocessorservice.service.log;

import cz.artin.vodafone.logprocessorservice.model.*;
import cz.artin.vodafone.logprocessorservice.repository.CountryRepository;
import cz.artin.vodafone.logprocessorservice.repository.ProcessedLogRepository;
import cz.artin.vodafone.logprocessorservice.service.log.analyzer.LogAnalyzer;
import cz.artin.vodafone.logprocessorservice.service.log.analyzer.LogAnalyzerResult;
import cz.artin.vodafone.logprocessorservice.service.log.downloader.LogDownloader;
import cz.artin.vodafone.logprocessorservice.service.log.dto.CountryCallStatisticsDto;
import cz.artin.vodafone.logprocessorservice.service.log.dto.MetricsDto;
import cz.artin.vodafone.logprocessorservice.service.log.parser.LogParser;
import cz.artin.vodafone.logprocessorservice.service.log.parser.LogParserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
// todo rename
public class LogService {

    private final LogDownloader logDownloader;
    private final LogParser logParser;
    private final LogAnalyzer logAnalyzer;
    private final ProcessedLogRepository processedLogRepository;
    private final CountryRepository countryRepository;

    public LogService(
            @Autowired LogDownloader logDownloader,
            @Autowired LogParser logParser,
            @Autowired LogAnalyzer logAnalyzer,
            @Autowired ProcessedLogRepository processedLogRepository,
            @Autowired CountryRepository countryRepository
    ) {
        this.logDownloader = logDownloader;
        this.logParser = logParser;
        this.logAnalyzer = logAnalyzer;
        this.processedLogRepository = processedLogRepository;
        this.countryRepository = countryRepository;
    }

    //@Transactional TODO
    public void selectLog(LocalDate date) throws IOException {
        var log = this.processedLogRepository.findById(date).orElse(null);

        if (log != null && log.isActive()) {
            return;
        }

        this.processedLogRepository.findActive()
                .ifPresent(otherLog -> otherLog.setActive(false));

        if (log == null) {
            var start = System.currentTimeMillis();

            var rawData = this.logDownloader.downloadRawLogs(date);
            var parsedData = this.logParser.parse(rawData);
            var analysis = this.logAnalyzer.analyze(parsedData.getItems());

            var processDuration = System.currentTimeMillis() - start;

            log = new ProcessedLog(
                    date,
                    rawData.length,
                    parsedData.getNumberOfRowsWithMissingFields(),
                    parsedData.getNumberOfRowsWithFieldErrors(),
                    analysis.getNumberOfCalls(),
                    analysis.getNumberOfOkCalls(),
                    analysis.getNumberOfKoCalls(),
                    analysis.getNumberOfMessages(),
                    analysis.getNumberOfBlankMessages(),
                    processDuration,
                    LocalDateTime.now()
            );

            processCountries(parsedData);
            processCallStatistics(analysis, log);
            processWordOccurrences(analysis, log);
        }

        log.setActive(true);
        this.processedLogRepository.saveAndFlush(log);
    }

    //    @Transactional(readOnly = true) TODO
    public MetricsDto buildMetrics(LocalDate date) {
        var log = this.processedLogRepository.findById(date)
                .orElseThrow(IllegalStateException::new);

        var countryCallStatistics =
                log.getCountryCallStatistics().stream().map(original -> new CountryCallStatisticsDto(
                        original.getId().getCallFrom().getCallingCode(),
                        original.getId().getCallTo().getCallingCode(),
                        original.getNumberOfCalls(),
                        original.getAverageCallDuration()
                )).collect(Collectors.toSet());

        var ratioOkCallsToKo =
                log.getNumberOfOkCalls() / (double) log.getNumberOfKoCalls();

        var wordOccurrences = log.getWordOccurrences()
                .stream()
                .collect(Collectors.toMap(
                        o -> o.getId().getWord(),
                        ProcessedLogWordOccurrence::getNumberOfOccurrences
                ));

        return new MetricsDto(
                log.getNumberOfRowsWithMissingFields(),
                log.getNumberOfMessagesWithBlankContent(),
                log.getNumberOfRowsWithFieldErrors(),
                countryCallStatistics,
                ratioOkCallsToKo,
                wordOccurrences
        );
    }

    private void processWordOccurrences(LogAnalyzerResult analysis, ProcessedLog log) {
        var wordOccurrences = analysis.getWordOccurrenceInMessages()
                .entrySet()
                .stream()
                .map(entry -> new ProcessedLogWordOccurrence(
                        new ProcessedLogWordOccurrenceId(
                                entry.getKey(),
                                log
                        ),
                        entry.getValue()
                ))
                .collect(Collectors.toSet());

        log.setWordOccurrences(wordOccurrences);
    }

    private void processCallStatistics(LogAnalyzerResult analysis, ProcessedLog log) {
        var stats = analysis.getCallStatsBetweenCountries().entrySet()
                .stream()
                .map(entry -> new ProcessedLogCountryCallStatistics(
                        new ProcessedLogCountryCallStatisticsId(
                                log,
                                this.countryRepository.findByCallingCode(entry.getKey().getOriginCountryCallingCode()).orElseThrow(),
                                this.countryRepository.findByCallingCode(entry.getKey().getDestinationCountryCallingCode()).orElseThrow()
                        ),
                        entry.getValue().getNumberOfCalls(),
                        entry.getValue().getAverageDuration()
                ))
                .collect(Collectors.toSet());

        log.setCountryCallStatistics(stats);
    }

    private void processCountries(LogParserResult parsedData) {
        parsedData.getItems()
                .forEach(mcpLogLine -> {
                    var originCountryCallingCode =
                            mcpLogLine.getBetween().getOriginCountryCallingCode();

                    var country = this.countryRepository.findByCallingCode(originCountryCallingCode)
                            .orElse(new Country(originCountryCallingCode));

                    country.setOrigin(true);

                    this.countryRepository.save(country);

                    var destinationCountryCallingCode =
                            mcpLogLine.getBetween().getDestinationCountryCallingCode();

                    country = this.countryRepository.findByCallingCode(destinationCountryCallingCode)
                            .orElse(new Country(destinationCountryCallingCode));

                    country.setDestination(true);

                    this.countryRepository.save(country);
                });
    }

}
