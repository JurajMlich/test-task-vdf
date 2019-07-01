package cz.artin.vodafone.logprocessorservice.service.log.parser;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import cz.artin.vodafone.logprocessorservice.service.log.parser.deserializer.McpLogLineDeserializer;
import cz.artin.vodafone.logprocessorservice.service.log.parser.dto.McpLogLine;
import cz.artin.vodafone.logprocessorservice.service.log.parser.exception.MissingPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

@Service
@Scope("prototype")
public class LogParser {

    private final Validator validator;
    private final ObjectMapper objectMapper;

    public LogParser(
            @Autowired ObjectMapper objectMapper,
            @Autowired Validator validator
    ) {
        this.objectMapper = objectMapper;
        this.validator = validator;

        var module = new SimpleModule("log-deserializer", Version.unknownVersion());
        module.addDeserializer(McpLogLine.class, new McpLogLineDeserializer());
        this.objectMapper.registerModule(module);
    }

    public LogParserResult parse(String[] lines) {
        var items = new ArrayList<McpLogLine>();
        var numberOfRowsWithMissingFields = 0;
        var numberOfRowsWithFieldErrors = 0;

        for (String line : lines) {
            try {
                var node = parseLine(line);

                if (validator.validate(node).size() > 0) {
                    numberOfRowsWithFieldErrors += 1;
                    continue;
                }

                items.add(node);
            } catch (MissingPropertyException e) {
                numberOfRowsWithMissingFields += 1;
            } catch (IOException | IllegalStateException | IllegalArgumentException e) {
                numberOfRowsWithFieldErrors += 1;
            }

        }

        return new LogParserResult(
                items,
                numberOfRowsWithMissingFields,
                numberOfRowsWithFieldErrors
        );

    }

    private McpLogLine parseLine(String line) throws IOException {
        line = line.trim();

        return this.objectMapper.readValue(
                new StringReader(line),
                McpLogLine.class
        );
    }
}
