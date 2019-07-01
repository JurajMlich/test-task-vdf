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
import java.util.List;

@Service
@Scope("prototype")
public class LogDownloader {
    private static final String URL = "https://raw.githubusercontent" + ".com" + "/vas-test/test1/master/logs/MCP_$DATE.json";
    private final DateTimeFormatter dateFormatter;

    public LogDownloader() {
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    }

    public String[] downloadRawLogs(LocalDate date) throws IOException {
        String rawUrl = URL.replace("$DATE", dateFormatter.format(date));
        URL url = new URL(rawUrl);


        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        List<String> lines = new ArrayList<>();

        String line;
        while ((line = rd.readLine()) != null) {
            lines.add(line);
        }
        rd.close();
        return lines.toArray(new String[0]);
    }
}
