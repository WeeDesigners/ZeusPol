package agh.edu.zeuspol.endpoints;

import agh.edu.zeuspol.datastructures.Rule;
import agh.edu.zeuspol.datastructures.storage.Policies;
import agh.edu.zeuspol.parsers.RuleJsonParser;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/policies")
public class PoliciesEndpoints {

  @PostMapping("/addPolicies")
  public String addPolicies(@RequestBody String policiesString) {
    // parse given JSON rules
    RuleJsonParser rjp = new RuleJsonParser(policiesString);

    rjp = rjp.parseJson();

    List<Rule> rules = rjp.getRules();

    // add policies to storage class
    Policies policies = Policies.getInstance();
    // INSECURE !!!!
    //        policies.addRules(rules);
    // SECURE c:
    if (policies.addRulesSecure(rules)) {
      return "Policies updated successfully!";
    }
    return "Policies updated unsuccessfully! One or more rules are not violent with SLA!";
  }

  @GetMapping("getPolicies")
  public String getPolicies() {
    Policies policies = Policies.getInstance();
    // TODO -> return JSON
    return policies.toString();
  }
}
