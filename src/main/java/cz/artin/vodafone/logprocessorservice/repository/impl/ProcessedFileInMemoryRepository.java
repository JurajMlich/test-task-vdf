package cz.artin.vodafone.logprocessorservice.repository.impl;

import cz.artin.vodafone.logprocessorservice.model.ProcessedFile;
import cz.artin.vodafone.logprocessorservice.repository.ProcessedFileRepository;

import java.time.LocalDate;
import java.util.*;

public class ProcessedFileInMemoryRepository implements ProcessedFileRepository {

    private Set<ProcessedFile> entities = new HashSet<>();

    @Override
    public ProcessedFile saveAndFlush(ProcessedFile file) {
        this.entities.add(file);
        return file;
    }

    @Override
    public Optional<ProcessedFile> findById(LocalDate file) {
        return this.entities.stream()
                .filter(processedFile -> processedFile.getDate().equals(file))
                .findFirst();
    }

    @Override
    public Optional<ProcessedFile> findActive() {
        return this.entities.stream()
                .filter(ProcessedFile::isActive)
                .findFirst();
    }

    @Override
    public List<ProcessedFile> findAll() {
        return new ArrayList<>(this.entities);
    }

    @Override
    public Integer countAll() {
        return this.entities.size();
    }

    @Override
    public Integer sumOfRows() {
        return this.entities.stream()
                .mapToInt(ProcessedFile::getNumberOfRows)
                .sum();
    }

    @Override
    public Integer sumOfCalls() {
        return this.entities.stream()
                .mapToInt(ProcessedFile::getNumberOfCalls)
                .sum();
    }

    @Override
    public Integer sumOfMessages() {
        return this.entities.stream()
                .mapToInt(ProcessedFile::getNumberOfMessages)
                .sum();
    }

    @Override
    public void delete(ProcessedFile file) {
        this.entities.remove(file);
    }
}
