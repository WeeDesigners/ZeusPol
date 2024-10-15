package agh.edu.zeuspol.services;

import agh.edu.zeuspol.datastructures.storage.Policies;
import agh.edu.zeuspol.datastructures.storage.Sla;
import agh.edu.zeuspol.datastructures.types.base.Rule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class HermesService {
  private final RestTemplate restTemplate;

  @Value("${hermes.url}")
  private String hermesUrl;

  public HermesService(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  public String addRuleObject(Rule rule) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Rule> requestEntity = new HttpEntity<>(rule, headers);

    try {
      return this.restTemplate.postForObject(
          hermesUrl + "/rules/addRuleObject", requestEntity, String.class);
    } catch (HttpClientErrorException e) {
      return e.getResponseBodyAsString();
    }
  }

  public String addRuleString(String ruleString) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> requestEntity = new HttpEntity<>(ruleString, headers);

    try {
      return this.restTemplate.postForObject(
          hermesUrl + "/rules/addRuleString", requestEntity, String.class);
    } catch (HttpClientErrorException e) {
      return e.getResponseBodyAsString();
    }
  }

  public Rule getRuleObject(long id) {
    Rule rule = restTemplate.getForObject(hermesUrl + "/rules/getRuleObject/{id}", Rule.class, id);
    if (rule == null) {
      // TODO -> some error or sth... idk....
    }
    return rule;
  }

  public String getRuleString(long id) {
    String ruleString =
        restTemplate.getForObject(hermesUrl + "/rules/getRuleString/{id}", String.class, id);
    if (ruleString == null || ruleString.isEmpty()) {
      // TODO -> some error or sth... idk....
    }
    return ruleString;
  }

  public Sla getSla() {
    Sla sla = restTemplate.getForObject(hermesUrl + "/sla/get", Sla.class);
    if (sla == null) {
      // TODO -> some error or sth... idk....
    }
    return sla;
  }

  public Policies getPolicies() {
    Policies policies = restTemplate.getForObject(hermesUrl + "/policies/get", Policies.class);
    if (policies == null) {
      // TODO -> some error or sth... idk....
    }
    return policies;
  }
}
