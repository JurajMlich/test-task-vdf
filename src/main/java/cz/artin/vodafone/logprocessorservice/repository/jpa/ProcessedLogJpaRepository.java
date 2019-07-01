package cz.artin.vodafone.logprocessorservice.repository.jpa;

import cz.artin.vodafone.logprocessorservice.model.ProcessedLog;
import cz.artin.vodafone.logprocessorservice.repository.ProcessedLogRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ProcessedLogJpaRepository extends JpaRepository<ProcessedLog, LocalDate>, ProcessedLogRepository {

    @Query("FROM ProcessedLog log WHERE log.active = true")
    Optional<ProcessedLog> findActive();

    @Query("SELECT COUNT (log) FROM ProcessedLog log")
    Integer countAll();

    @Query("SELECT SUM(log.numberOfRows) FROM ProcessedLog log")
    Integer sumOfRows();

    @Query("SELECT SUM(log.numberOfCalls) FROM ProcessedLog log")
    Integer sumOfCalls();

    @Query("SELECT SUM(log.numberOfMessages) FROM ProcessedLog log")
    Integer sumOfMessages();
}
