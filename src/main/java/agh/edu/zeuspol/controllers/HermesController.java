package agh.edu.zeuspol.controllers;

import agh.edu.zeuspol.datastructures.storage.Policies;
import agh.edu.zeuspol.datastructures.storage.Sla;
import agh.edu.zeuspol.datastructures.storage.Slas;
import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.datastructures.types.attributes.ExecutionRequest;
import agh.edu.zeuspol.services.HermesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hermes")
public class HermesController {

    @Autowired
    private HermesService hermesService;

    @PostMapping("")
    public ResponseEntity<?> getAll(){
        List<PolicyRule> policyRules = hermesService.getPolicyRules();
        Policies.newInstance().addRules(policyRules);

        List<Sla> slaList = hermesService.getAllSlas();
        Slas.newInstance().addSlaList(slaList);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/policies")
    public ResponseEntity<?> getPolicies() {
        List<PolicyRule> policyRules = hermesService.getPolicyRules();
        Policies.newInstance().addRules(policyRules);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sla")
    public ResponseEntity<?> getSlas() {
        List<Sla> slaList = hermesService.getAllSlas();
        Slas.newInstance().addSlaList(slaList);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sla/{id}")
    public ResponseEntity<?> getSla(@PathVariable("id") long id) {
        Sla sla = hermesService.getSla(id);
        Slas.getInstance().addSla(sla);
        return ResponseEntity.ok().build();
    }

}
