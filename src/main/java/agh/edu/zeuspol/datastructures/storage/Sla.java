package agh.edu.zeuspol.datastructures.storage;

import agh.edu.zeuspol.datastructures.NotificationRule;
import agh.edu.zeuspol.datastructures.Rule;

import java.util.ArrayList;
import java.util.List;

public class Sla {

    private static final Sla instance = new Sla();

    private final List<Rule> rules;
    private final List<NotificationRule> notificationRules;

    private Sla(){
        this.rules = new ArrayList<>();
        this.notificationRules = new ArrayList<>();
    }

    public static Sla getInstance(){
        return instance;
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

    public void removeRules(){
        this.rules.clear();
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

    public void removeNotificationRules(){
        this.notificationRules.clear();
    }

    public void clearSla(){
        removeRules();
        removeNotificationRules();
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
