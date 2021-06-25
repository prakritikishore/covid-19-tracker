package com.prakritis.covid19tracker.services;

import com.prakritis.covid19tracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {

    private final String dataUrl = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<LocationStats> statsRecords = new ArrayList<>();

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {

        List<LocationStats> newStats = new ArrayList<>();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(dataUrl)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader in = new StringReader(response.body());
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
        for (CSVRecord record : records) {
            LocationStats stats = new LocationStats();
            stats.setCountry(record.get("Country/Region"));
            stats.setState(record.get("Province/State"));
            Integer caseCounts = Integer.parseInt(record.get(record.size() - 1));
            Integer prevCase = Integer.parseInt(record.get(record.size() - 2));
            stats.setLatestCaseCounts(caseCounts);
            stats.setDiffFromPrevDay(caseCounts - prevCase);
            newStats.add(stats);
        }

        this.statsRecords = newStats;
    }

    public List<LocationStats> getStatsRecords() {
        return statsRecords;
    }
}
