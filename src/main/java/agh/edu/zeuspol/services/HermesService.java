package agh.edu.zeuspol.services;

import agh.edu.zeuspol.datastructures.storage.Policies;
import agh.edu.zeuspol.datastructures.storage.Sla;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HermesService {
  private final RestTemplate restTemplate;

  @Value("${hermes.url}")
  private String hermesUrl;

  public HermesService(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  public List<Sla> getAllSlas() {
    ResponseEntity<Sla[]> slasEntity = restTemplate.getForEntity(hermesUrl + "/sla", Sla[].class);
    Sla[] slas = slasEntity.getBody();
    if (slas != null) {
      return Arrays.asList(slas);
    }
    return new ArrayList<>();
  }

  public Sla getSla(long id) {
    Sla sla = restTemplate.getForObject(hermesUrl + "/sla/" + id, Sla.class);
    if (sla == null) {
      // TODO -> some error or sth... idk....
    }
    return sla;
  }

  public Policies getPolicies() {
    Policies policies = restTemplate.getForObject(hermesUrl + "/policies/active", Policies.class);
    if (policies == null) {
      // TODO -> some error or sth... idk....
    }
    return policies;
  }
}
