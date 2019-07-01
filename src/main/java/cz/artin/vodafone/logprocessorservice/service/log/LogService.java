package cz.artin.vodafone.logprocessorservice.service.log;

import cz.artin.vodafone.logprocessorservice.model.*;
import cz.artin.vodafone.logprocessorservice.repository.CountryRepository;
import cz.artin.vodafone.logprocessorservice.repository.ProcessedFileRepository;
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
    private final ProcessedFileRepository processedFileRepository;
    private final CountryRepository countryRepository;

    public LogService(
            @Autowired LogDownloader logDownloader,
            @Autowired LogParser logParser,
            @Autowired LogAnalyzer logAnalyzer,
            @Autowired ProcessedFileRepository processedFileRepository,
            @Autowired CountryRepository countryRepository
    ) {
        this.logDownloader = logDownloader;
        this.logParser = logParser;
        this.logAnalyzer = logAnalyzer;
        this.processedFileRepository = processedFileRepository;
        this.countryRepository = countryRepository;
    }

    //@Transactional TODO
    public void process(LocalDate date) throws IOException {
        var file = this.processedFileRepository.findById(date).orElse(null);

        if (file != null && file.isActive()) {
            return;
        }

        this.processedFileRepository.findActive()
                .ifPresent(processedFile -> processedFile.setActive(false));

        if (file == null) {
            var start = System.currentTimeMillis();

            var rawData = this.logDownloader.downloadRawLogs(date);
            var parsedData = this.logParser.parse(rawData);
            var analysis = this.logAnalyzer.analyze(parsedData.getItems());

            var processDuration = System.currentTimeMillis() - start;

            file = this.saveProcessedResult(
                    date,
                    parsedData,
                    analysis,
                    processDuration,
                    rawData.length
            );
        }

        file.setActive(true);
    }

    private ProcessedFile saveProcessedResult(
            LocalDate date,
            LogParserResult parsedData,
            LogAnalyzerResult analysis,
            long processDuration,
            int numberOfRows
    ) {

        var file = new ProcessedFile(
                date,
                numberOfRows,
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

        file.setActive(true);

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

        var stats = analysis.getCallStatsBetweenCountries()
                .entrySet()
                .stream()
                .map(entry -> new ProcessedFileCountryCallStatistics(
                        new ProcessedFileCountryCallStatisticsId(
                                file,
                                this.countryRepository.findByCallingCode(entry.getKey().getOriginCountryCallingCode()).orElseThrow(),
                                this.countryRepository.findByCallingCode(entry.getKey().getDestinationCountryCallingCode()).orElseThrow()
                        ),
                        entry.getValue().getNumberOfCalls(),
                        entry.getValue().getAverageDuration()
                ))
                .collect(Collectors.toSet());

        file.setCountryCallStatistics(stats);

        var wordOccurrences = analysis.getWordOccurrenceInMessages()
                .entrySet()
                .stream()
                .map(entry -> new ProcessedFileWordOccurrence(
                        new ProcessedFileWordOccurrenceId(
                                entry.getKey(),
                                file
                        ),
                        entry.getValue()
                ))
                .collect(Collectors.toSet());

        file.setWordOccurrences(wordOccurrences);

        this.processedFileRepository.saveAndFlush(file);

        return file;
    }

    //    @Transactional(readOnly = true) TODO
    public MetricsDto buildMetrics(LocalDate date) {
        var file = this.processedFileRepository.findById(date)
                .orElseThrow(IllegalStateException::new);

        var countryCallStatistics =
                file.getCountryCallStatistics().stream().map(original -> new CountryCallStatisticsDto(
                        original.getId().getCallFrom().getCallingCode(),
                        original.getId().getCallTo().getCallingCode(),
                        original.getNumberOfCalls(),
                        original.getAverageCallDuration()
                )).collect(Collectors.toSet());

        var ratioOkCallsToKo =
                file.getNumberOfOkCalls() / (double) file.getNumberOfKoCalls();

        var wordOccurrences = file.getWordOccurrences()
                .stream()
                .collect(Collectors.toMap(
                        o -> o.getId().getWord(),
                        ProcessedFileWordOccurrence::getNumberOfOccurrences
                ));

        return new MetricsDto(
                file.getNumberOfRowsWithMissingFields(),
                file.getNumberOfMessagesWithBlankContent(),
                file.getNumberOfRowsWithFieldErrors(),
                countryCallStatistics,
                ratioOkCallsToKo,
                wordOccurrences
        );
    }
}