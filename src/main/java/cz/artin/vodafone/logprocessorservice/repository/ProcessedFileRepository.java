package cz.artin.vodafone.logprocessorservice.repository;

import cz.artin.vodafone.logprocessorservice.model.ProcessedFile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessedFileRepository {

    ProcessedFile saveAndFlush(ProcessedFile file);

    Optional<ProcessedFile> findById(LocalDate file);

    Optional<ProcessedFile> findActive();

    List<ProcessedFile> findAll();

    Integer countAll();

    Integer sumOfRows();

    Integer sumOfCalls();

    Integer sumOfMessages();

    void delete(ProcessedFile file);
}
