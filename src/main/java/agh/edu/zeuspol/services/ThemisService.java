package agh.edu.zeuspol.services;

import io.github.hephaestusmetrics.model.metrics.Metric;
import io.github.hephaestusmetrics.model.queryresults.RawQueryResult;
import io.github.hephaestusmetrics.serialization.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ThemisService {
    private final RestTemplate restTemplate;
    @Value("${themis.url}")
    private String themisUrl;

    public ThemisService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getActions() {
        RawQueryResult[] rawActions = restTemplate.getForObject(themisUrl + "/actions", RawQueryResult[].class);
        return Arrays.stream(rawActions).toString();
    }
}
