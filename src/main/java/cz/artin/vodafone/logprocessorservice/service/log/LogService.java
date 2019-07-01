package cz.artin.vodafone.logprocessorservice.service.log;

import cz.artin.vodafone.logprocessorservice.model.*;
import cz.artin.vodafone.logprocessorservice.repository.CountryRepository;
import cz.artin.vodafone.logprocessorservice.repository.ProcessedFileRepository;
import cz.artin.vodafone.logprocessorservice.service.log.analyzer.LogAnalyzer;
import cz.artin.vodafone.logprocessorservice.service.log.analyzer.LogAnalyzerResult;
import cz.artin.vodafone.logprocessorservice.service.log.downloader.LogDownloader;
import cz.artin.vodafone.logprocessorservice.service.log.parser.LogParser;
import cz.artin.vodafone.logprocessorservice.service.log.parser.LogParserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
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

    public void process(LocalDate date) throws IOException {
        var file = this.processedFileRepository.findById(date).orElse(null);

        if (file != null) {
            this.processedFileRepository.delete(file);
            file = null;
        }

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

        // todo: currently selected one
        System.out.println();
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


        // save countries, streams used to filter out duplicates (database
        // latency will be higher than CPU overhead of filtering out duplicates)
        Stream.concat(
                parsedData.getItems()
                        .stream()
                        .map(mcpLogLine -> mcpLogLine.getBetween().getOrigin()),
                parsedData.getItems()
                        .stream()
                        .map(mcpLogLine -> mcpLogLine.getBetween().getDestination())
        )
                .distinct()
                .forEach((countryRepository::save));

        var stats = analysis.getCallStatsBetweenCountries()
                .entrySet()
                .stream()
                .map(entry -> new ProcessedFileCountryCallStatistics(
                        new ProcessedFileCountryCallStatisticsId(
                                file,
                                entry.getKey().getOrigin(),
                                entry.getKey().getDestination()
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
}
