package agh.edu.zeuspol.endpoints;


import agh.edu.zeuspol.datastructures.NotificationRule;
import agh.edu.zeuspol.datastructures.Rule;
import agh.edu.zeuspol.datastructures.storages.Sla;
import agh.edu.zeuspol.parsers.RuleJsonParser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sla")
public class SlaEndpoint {

    @PostMapping("/addSla")
    public String addSla(@RequestBody String slaString){
        //parse given JSON rule
        RuleJsonParser rjp = new RuleJsonParser(slaString);

        rjp = rjp.parseJson();

        List<Rule> rules = rjp.getRules();
        List<NotificationRule> notificationRules = rjp.getNotificationRules();

        //add rules to SLA
        Sla sla = Sla.getInstance();
        sla.addRules(rules);
        sla.addNotificationRules(notificationRules);


        return "Sla:\n" + sla.toString();
    }



}
