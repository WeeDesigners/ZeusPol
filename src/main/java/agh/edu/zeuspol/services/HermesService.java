package agh.edu.zeuspol.services;

import agh.edu.zeuspol.datastructures.Rule;
import agh.edu.zeuspol.endpoints.requests.ExecutionRequest;
import io.github.hephaestusmetrics.model.queryresults.RawQueryResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class HermesService {
    private final RestTemplate restTemplate;

//    @Value("${hermes.url}")
    private String hermesUrl = "127.0.0.2:8080";

    public HermesService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String addRuleObject(Rule rule) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Rule> requestEntity = new HttpEntity<>(rule, headers);

        try {
            return this.restTemplate.postForObject(hermesUrl + "/rules/addRuleObject", requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            return e.getResponseBodyAsString();
        }

    }

    public String addRuleString(String ruleString) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(ruleString, headers);

        try {
            return this.restTemplate.postForObject(hermesUrl + "/rules/addRuleString", requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            return e.getResponseBodyAsString();
        }

    }

    public Rule getRuleObject(long id) {
        Rule rule = restTemplate.getForObject(hermesUrl + "/rules/getRuleObject", Rule.class);
        if(rule == null) {
            //TODO -> some error or sth... idk....
        }
        return rule;
    }

    public String getRuleString(long id) {
        String ruleString = restTemplate.getForObject(hermesUrl + "/rules/getRuleString", String.class);
        if(ruleString == null || ruleString.isEmpty()) {
            //TODO -> some error or sth... idk....
        }
        return ruleString;
    }
}

