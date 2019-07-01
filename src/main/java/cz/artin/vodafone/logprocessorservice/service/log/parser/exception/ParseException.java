package cz.artin.vodafone.logprocessorservice.service.log.parser.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author Juraj Mlich <juraj.mlich@artin.cz>
 */
public class ParseException extends JsonProcessingException {

    public ParseException(String message) {
        super(message);
    }
}
