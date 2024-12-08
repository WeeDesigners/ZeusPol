package agh.edu.zeuspol.services;

import agh.edu.zeuspol.datastructures.types.attributes.ExecutionRequest;
import io.github.hephaestusmetrics.model.queryresults.RawQueryResult;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ThemisService {
  private final RestTemplate restTemplate;

  @Value("${themis.url}")
  private String themisUrl;

  public ThemisService(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  public String getActions() {
    RawQueryResult[] rawActions =
        restTemplate.getForObject(themisUrl + "/actions", RawQueryResult[].class);
    if (rawActions != null && rawActions.length > 0) {
      return Arrays.stream(rawActions).toString();
    }
    return "";
  }

  public String executeAction(ExecutionRequest request) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<ExecutionRequest> requestEntity = new HttpEntity<ExecutionRequest>(request, headers);

    try {
      return this.restTemplate.postForObject(themisUrl + "/execute", requestEntity, String.class);
    } catch (HttpClientErrorException e) {
      return e.getResponseBodyAsString();
    }
  }
}
