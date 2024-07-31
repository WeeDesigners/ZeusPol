package agh.edu.zeuspol.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import io.github.hephaestusmetrics.model.metrics.Metric;
import io.github.hephaestusmetrics.model.queryresults.RawQueryResult;
import io.github.hephaestusmetrics.serialization.Translator;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service()
public class HephaestusQueryService {
    private final Translator translator;
    private final RestTemplate restTemplate;
    @Value("${hephaestus.url}")
    private String url;
    public HephaestusQueryService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        this.translator = new Translator();
    }

    public List<Metric> getMetrics() {
        RawQueryResult[] rawMetrics = restTemplate.getForObject(url + "/hephaestus/metrics/selected", RawQueryResult[].class);
        return Arrays.stream(Objects.requireNonNullElse(rawMetrics, new RawQueryResult[]{}))
                .map(translator::parseResult)
                .flatMap(result -> result.getMetrics().stream())
                .collect(Collectors.toList());
    }
}