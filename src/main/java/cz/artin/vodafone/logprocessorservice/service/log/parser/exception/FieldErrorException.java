package cz.artin.vodafone.logprocessorservice.service.log.parser.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

public class FieldErrorException extends JsonProcessingException {

    public FieldErrorException(String message) {
        super(message);
    }
}
