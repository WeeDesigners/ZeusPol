package agh.edu.zeuspol.datastructures;

import java.util.ArrayList;
import java.util.List;

public class Sla {

    private final List<Rule> rules;
    private final List<NotificationRule> notificationRules;

    public Sla(){
        this.rules = new ArrayList<>();
        this.notificationRules = new ArrayList<>();
    }

    public Sla(List<Rule> rules, List<NotificationRule> notificationRules){
        this.rules = rules;
        this.notificationRules = notificationRules;
    }

    public void addRules(List<Rule> rules){
        this.rules.addAll(rules);
    }

    public void addNotificationRules(List<NotificationRule> notificationRules){
        this.notificationRules.addAll(notificationRules);
    }

    public void addRule(Rule rule){
        this.rules.add(rule);
    }

    public void addNotificationRule(NotificationRule notificationRule){
        this.notificationRules.add(notificationRule);
    }

    public boolean checkIfPolicyIsNotDirectlyInconsistentWithSLA(Rule rule){
        // TODO -> eventually change the name or sth...
        return true;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public List<NotificationRule> getNotificationRules() {
        return notificationRules;
    }


    






}
