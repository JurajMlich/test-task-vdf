package cz.artin.vodafone.logprocessorservice.repository.impl;

import cz.artin.vodafone.logprocessorservice.model.ProcessedLog;
import cz.artin.vodafone.logprocessorservice.repository.ProcessedLogRepository;

import java.time.LocalDate;
import java.util.*;

/**
 * In memory implementation of {@link ProcessedLogRepository}. For development
 * purposes only. <b>Thread unsafe!</b>
 *
 * @author Juraj Mlich <juraj.mlich@artin.cz>
 */
public class ProcessedLogInMemoryRepository implements ProcessedLogRepository {

    private Set<ProcessedLog> entities = new HashSet<>();

    @Override
    public ProcessedLog saveAndFlush(ProcessedLog log) {
        this.entities.add(log);
        return log;
    }

    @Override
    public Optional<ProcessedLog> findById(LocalDate log) {
        return this.entities.stream()
                .filter(processedFile -> processedFile.getDate().equals(log))
                .findFirst();
    }

    @Override
    public Optional<ProcessedLog> findActive() {
        return this.entities.stream()
                .filter(ProcessedLog::isActive)
                .findFirst();
    }

    @Override
    public List<ProcessedLog> findAll() {
        return new ArrayList<>(this.entities);
    }

    @Override
    public Integer countAll() {
        return this.entities.size();
    }

    @Override
    public Integer sumOfRows() {
        return this.entities.stream()
                .mapToInt(ProcessedLog::getNumberOfRows)
                .sum();
    }

    @Override
    public Integer sumOfCalls() {
        return this.entities.stream()
                .mapToInt(ProcessedLog::getNumberOfCalls)
                .sum();
    }

    @Override
    public Integer sumOfMessages() {
        return this.entities.stream()
                .mapToInt(ProcessedLog::getNumberOfMessages)
                .sum();
    }
}
