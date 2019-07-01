package cz.artin.vodafone.logprocessorservice.service.log.parser.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

public class MissingPropertyException extends JsonProcessingException {

    public MissingPropertyException(String message) {
        super(message);
    }
}
