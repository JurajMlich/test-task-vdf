package cz.artin.vodafone.logprocessorservice.service.log.parser;


import cz.artin.vodafone.logprocessorservice.service.log.parser.dto.McpLogLine;

import java.util.List;

/**
 * @author Juraj Mlich <juraj.mlich@artin.cz>
 */
public class LogParserResult {

    private List<McpLogLine> items;
    private int numberOfRowsWithMissingFields;
    private int numberOfRowsWithFieldErrors;

    LogParserResult(
            List<McpLogLine> items,
            int numberOfRowsWithMissingFields,
            int numberOfRowsWithFieldErrors
    ) {
        this.items = items;
        this.numberOfRowsWithMissingFields = numberOfRowsWithMissingFields;
        this.numberOfRowsWithFieldErrors = numberOfRowsWithFieldErrors;
    }

    public List<McpLogLine> getItems() {
        return items;
    }

    public int getNumberOfRowsWithMissingFields() {
        return numberOfRowsWithMissingFields;
    }

    public int getNumberOfRowsWithFieldErrors() {
        return numberOfRowsWithFieldErrors;
    }
}
