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

    public Rule removeRule(long id){
        for(Rule rule : rules){
            if(rule.id == id){
                rules.remove(rule);
                return rule;
            }
        }
        return null;
    }

    public NotificationRule removeNotificationRule(long id){
        for(NotificationRule notificationRule : notificationRules){
            if(notificationRule.id == id){
                notificationRules.remove(notificationRule);
                return notificationRule;
            }
        }
        return null;
    }

    public List<Rule> getRules() {
        return new ArrayList<>(rules);
    }

    public List<NotificationRule> getNotificationRules() {
        return new ArrayList<>(notificationRules);
    }



    @Override
    public String toString() {
        //TODO -> better concat
        return "\n====================================\n Rules:\n"
                + rules + "\n Notification Rules:\n"
                + notificationRules
                + "\n====================================\n";
    }








}
