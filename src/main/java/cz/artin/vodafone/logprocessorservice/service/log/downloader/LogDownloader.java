package cz.artin.vodafone.logprocessorservice.service.log.downloader;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
@Scope("prototype")
public class LogDownloader {

    private static final String URL = "https://raw.githubusercontent" + ".com" + "/vas-test/test1/master/logs/MCP_$DATE.json";
    private static final DateTimeFormatter DATE_FORMATTER;

    static {
        DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    }

    public String[] downloadRawLogs(LocalDate date) throws IOException {
        // todo chunking
        var url = URL.replace("$DATE", DATE_FORMATTER.format(date));

        var connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        var reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        var lines = new ArrayList<String>();

        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines.toArray(new String[0]);
    }
}
