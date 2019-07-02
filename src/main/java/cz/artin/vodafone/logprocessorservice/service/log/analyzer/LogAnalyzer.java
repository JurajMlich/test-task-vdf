package cz.artin.vodafone.logprocessorservice.service.log.analyzer;

import cz.artin.vodafone.logprocessorservice.service.log.parser.dto.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Class analyzing special properties of a list of {@link McpLogLine}.
 *
 * @author Juraj Mlich <juraj.mlich@artin.cz>
 */
@Service
@Scope("prototype")
public class LogAnalyzer {

    /**
     * Analyse word occurrence of these words in messages.
     */
    static final String[] ANALYZE_WORDS = {"HELLO", "YOU", "FINE", "ARE", "NOT"};
    /**
     * @see #ANALYZE_WORD_PATTERNS
     */
    static final Map<String, Pattern> ANALYZE_WORD_PATTERNS = new HashMap<>();

    static {
        for (String analyzeWord : ANALYZE_WORDS) {
            ANALYZE_WORD_PATTERNS.put(
                    analyzeWord,
                    Pattern.compile("\\b" + analyzeWord.toLowerCase() + "\\b")
            );
        }
    }

    public LogAnalyzerResult analyze(List<McpLogLine> items) {
        var numberOfCalls = 0;
        var numberOfMessages = 0;
        var numberOfOkCalls = 0;
        var numberOfKoCalls = 0;
        var numberOfBlankMessages = 0;

        var callStatsBetweenCountries =
                new HashMap<McpCommunicationBetweenCountries, CallStats>();
        var wordOccurrenceInMessages =
                new HashMap<String, Integer>();

        for (McpLogLine mcpLogLine : items) {
            if (mcpLogLine instanceof McpLogLineCall) {
                var call = (McpLogLineCall) mcpLogLine;
                numberOfCalls++;

                if (McpCallStatusCode.OK.equals(call.getStatus())) {
                    numberOfOkCalls++;
                } else if (McpCallStatusCode.KO.equals(call.getStatus())) {
                    numberOfKoCalls++;
                }

                analyzeCallStatsBetweenCountries(callStatsBetweenCountries, call);
            } else if (mcpLogLine instanceof McpLogLineMessage) {
                var message = (McpLogLineMessage) mcpLogLine;
                numberOfMessages++;

                if (message.getMessageContent() == null ||
                        message.getMessageContent().isEmpty()) {
                    numberOfBlankMessages++;
                }

                analyzeWordOccurrences(wordOccurrenceInMessages, message);
            }
        }

        return new LogAnalyzerResult(
                numberOfCalls,
                numberOfMessages,
                callStatsBetweenCountries,
                wordOccurrenceInMessages,
                numberOfBlankMessages,
                numberOfOkCalls,
                numberOfKoCalls
        );
    }

    private void analyzeWordOccurrences(
            Map<String, Integer> wordOccurrenceInMessages,
            McpLogLineMessage message
    ) {
        var messageContent = message.getMessageContent().toLowerCase();

        for (String word : ANALYZE_WORDS) {
            var matcher = ANALYZE_WORD_PATTERNS.get(word).matcher(messageContent);
            var occurrences = 0;

            while (matcher.find()) {
                occurrences++;
            }

            var newCount = wordOccurrenceInMessages.getOrDefault(word, 0) + occurrences;

            wordOccurrenceInMessages.put(word, newCount);
        }
    }

    private void analyzeCallStatsBetweenCountries(
            Map<McpCommunicationBetweenCountries, CallStats> callStatsBetweenCountries,
            McpLogLineCall call
    ) {
        var stats = callStatsBetweenCountries.getOrDefault(
                call.getBetween(), new CallStats(0, 0)
        );

        // calculate average
        var total = stats.getAverageDuration() * stats.getNumberOfCalls() + call.getDuration();
        var newCount = stats.getNumberOfCalls() + 1;

        stats.setAverageDuration(total / newCount);
        stats.setNumberOfCalls(newCount);

        callStatsBetweenCountries.put(call.getBetween(), stats);
    }
}
