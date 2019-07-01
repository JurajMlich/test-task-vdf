package cz.artin.vodafone.logprocessorservice.service.log.parser.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author Juraj Mlich <juraj.mlich@artin.cz>
 */
public class FieldErrorException extends JsonProcessingException {

    public FieldErrorException(String message) {
        super(message);
    }
}
