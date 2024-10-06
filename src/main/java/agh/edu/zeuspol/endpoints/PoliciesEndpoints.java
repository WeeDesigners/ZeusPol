package agh.edu.zeuspol.endpoints;

import agh.edu.zeuspol.datastructures.Rule;
import agh.edu.zeuspol.datastructures.storage.Policies;
import agh.edu.zeuspol.parsers.RuleJsonParser;
import java.util.List;

import agh.edu.zeuspol.services.HermesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/policies")
public class PoliciesEndpoints {

  private final HermesService hermesService;

  @Autowired
  public PoliciesEndpoints(HermesService hermesService) {
    this.hermesService = hermesService;
  }


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

  @PostMapping("/exportAll")
  public String exportAllToHermes(){
    Policies policies = Policies.getInstance();
    List<Rule> rules = policies.getRules();

    String response = "";

    for(Rule rule : rules){
      String hermesResponse = hermesService.addRuleObject(rule);
      //these '+=' are really cute c:
      if(hermesResponse != null && hermesResponse.equals("Rule added successfully")){
        response += "Rule id="+rule.id+" exported succesfully!\n";
      }
      else{
        response += "Rule id="+rule.id+" not exported\n";
      }
    }
    return response;
  }

  @PostMapping("/importRule")
  public String importRuleFromHermes(@RequestBody long id){
    Policies policies = Policies.getInstance();

    Rule rule = hermesService.getRuleObject(id);
    if(rule != null){
      policies.addRule(rule);
      return "Successfully imported rule: "+rule;
    }
    return "Error occurred while importing rule id="+id;
  }

  @GetMapping("getPolicies")
  public String getPolicies() {
    Policies policies = Policies.getInstance();
    // TODO -> return JSON
    return policies.toString();
  }
}
