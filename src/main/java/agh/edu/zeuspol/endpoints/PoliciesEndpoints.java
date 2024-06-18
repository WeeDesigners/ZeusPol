package agh.edu.zeuspol.endpoints;


import agh.edu.zeuspol.datastructures.Rule;
import agh.edu.zeuspol.datastructures.storage.Policies;
import agh.edu.zeuspol.parsers.RuleJsonParser;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/policies")
public class PoliciesEndpoints {

    @PostMapping("/addPolicies")
    public String addPolicies(@RequestBody String policiesString){
        //parse given JSON rules
        RuleJsonParser rjp = new RuleJsonParser(policiesString);

        rjp = rjp.parseJson();

        List<Rule> rules = rjp.getRules();

        //add policies to storage class
        Policies policies = Policies.getInstance();
        policies.addRules(rules);

        //string response
        return "Policies updated successfully";
    }

    @GetMapping("getPolicies")
    public String getPolicies(){
        Policies policies = Policies.getInstance();
        return policies.toString();
    }

}
