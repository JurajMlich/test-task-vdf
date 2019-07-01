package cz.artin.vodafone.logprocessorservice.repository;

import cz.artin.vodafone.logprocessorservice.model.ProcessedLog;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Storage for {@link ProcessedLog}.
 *
 * @author Juraj Mlich <juraj.mlich@artin.cz>
 */
@Repository
public interface ProcessedLogRepository {

    ProcessedLog saveAndFlush(ProcessedLog log);

    Optional<ProcessedLog> findById(LocalDate id);

    Optional<ProcessedLog> findActive();

    List<ProcessedLog> findAll();

    Integer countAll();

    Integer sumOfRows();

    Integer sumOfCalls();

    Integer sumOfMessages();
}
