package agh.edu.zeuspol.controllers;

import agh.edu.zeuspol.ZeuspolApplication;
import agh.edu.zeuspol.datastructures.storage.Policies;
import agh.edu.zeuspol.datastructures.storage.Sla;
import agh.edu.zeuspol.datastructures.storage.Slas;
import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.services.HermesService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hermes")
public class HermesController {

  @Autowired private HermesService hermesService;

  @PostMapping("")
  public ResponseEntity<?> getAll() {
    List<PolicyRule> policyRules = hermesService.getPolicyRules();
    Policies.newInstance().addRules(policyRules);

    List<Sla> slaList = hermesService.getAllSlas();
    Slas.newInstance().addSlaList(slaList);
    ZeuspolApplication.buildExecutor();
    return ResponseEntity.ok().build();
  }

  @PostMapping("/policies")
  public ResponseEntity<?> getPolicies() {
    List<PolicyRule> policyRules = hermesService.getPolicyRules();
    Policies.newInstance().addRules(policyRules);
    ZeuspolApplication.buildExecutor();
    return ResponseEntity.ok().build();
  }

  @PostMapping("/sla")
  public ResponseEntity<?> getSlas() {
    List<Sla> slaList = hermesService.getAllSlas();
    Slas.newInstance().addSlaList(slaList);
    ZeuspolApplication.buildExecutor();
    return ResponseEntity.ok().build();
  }

  @PostMapping("/sla/{id}")
  public ResponseEntity<?> getSla(@PathVariable("id") long id) {
    Sla sla = hermesService.getSla(id);
    Slas.getInstance().addSla(sla);
    ZeuspolApplication.buildExecutor();
    return ResponseEntity.ok().build();
  }
}
