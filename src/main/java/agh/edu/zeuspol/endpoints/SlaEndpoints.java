package agh.edu.zeuspol.endpoints;


import agh.edu.zeuspol.datastructures.NotificationRule;
import agh.edu.zeuspol.datastructures.Rule;
import agh.edu.zeuspol.datastructures.storage.Sla;
import agh.edu.zeuspol.parsers.RuleJsonParser;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sla")
public class SlaEndpoints {

    @PostMapping("/addSla")
    public String addSla(@RequestBody String slaString){
        //parse given JSON rules
        RuleJsonParser rjp = new RuleJsonParser(slaString);

        rjp = rjp.parseJson();

        List<Rule> rules = rjp.getRules();
        List<NotificationRule> notificationRules = rjp.getNotificationRules();

        //add rules to SLA
        Sla sla = Sla.getInstance();
        sla.addRules(rules);
        sla.addNotificationRules(notificationRules);

        //string response
        return "SLA updated successfully";
    }

    @GetMapping("/getSla")
    public String getSla(){
        Sla sla = Sla.getInstance();
        return sla.toString();
    }


}
