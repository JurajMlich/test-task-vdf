package cz.artin.vodafone.logprocessorservice.repository.jpa;

import cz.artin.vodafone.logprocessorservice.model.ProcessedFile;
import cz.artin.vodafone.logprocessorservice.repository.ProcessedFileRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ProcessedFileJpaRepository extends JpaRepository<ProcessedFile, LocalDate>, ProcessedFileRepository {

    @Query("FROM ProcessedFile file WHERE file.active = true")
    Optional<ProcessedFile> findActive();

    @Query("SELECT COUNT (file) FROM ProcessedFile file")
    Integer countAll();

    @Query("SELECT SUM(file.numberOfRows) FROM ProcessedFile file")
    Integer sumOfRows();

    @Query("SELECT SUM(file.numberOfCalls) FROM ProcessedFile file")
    Integer sumOfCalls();

    @Query("SELECT SUM(file.numberOfMessages) FROM ProcessedFile file")
    Integer sumOfMessages();
}
