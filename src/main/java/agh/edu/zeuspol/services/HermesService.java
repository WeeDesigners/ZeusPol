package agh.edu.zeuspol.services;

import agh.edu.zeuspol.datastructures.storage.Policies;
import agh.edu.zeuspol.datastructures.storage.Sla;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class HermesService {
  private final RestTemplate restTemplate;

  @Value("${hermes.url}")
  private String hermesUrl;

  public HermesService(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }


  public List<Sla> getAllSlas() {
    List<Sla> result = new ArrayList<>();
    List slas = restTemplate.getForObject(hermesUrl + "/sla", List.class);
    if (slas != null) {
      for(Object sla : slas) {
        result.add((Sla) sla);
      }
    }
    return result;
  }

  public Sla getSla(long id) {
    Sla sla = restTemplate.getForObject(hermesUrl + "/sla/" + id, Sla.class);
    if (sla == null) {
      // TODO -> some error or sth... idk....
    }
    return sla;
  }

  public Policies getPolicies() {
    Policies policies = restTemplate.getForObject(hermesUrl + "/policies", Policies.class);
    if (policies == null) {
      // TODO -> some error or sth... idk....
    }
    return policies;
  }
}
