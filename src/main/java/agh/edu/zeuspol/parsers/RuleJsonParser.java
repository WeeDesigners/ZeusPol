package agh.edu.zeuspol.parsers;


import agh.edu.zeuspol.data_structures.NotificationRule;
import agh.edu.zeuspol.data_structures.Rule;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

public class RuleJsonParser {

    private final String jsonString;
    private List<Rule> rules;
    private List<NotificationRule> notificationRules;

    public RuleJsonParser(String jsonString){
        this.jsonString = jsonString;
    }

    public void parseJson(){

        Gson gson = new Gson();


        JsonObject[] jsonObjects = gson.fromJson(jsonString, JsonObject[].class);

        for(JsonObject jsonObject : jsonObjects){
            if(jsonObject.has("email")){
                notificationRules.add(gson.fromJson(jsonObject, NotificationRule.class));
            }
            else{
                rules.add(gson.fromJson(jsonObject, Rule.class));
            }
        }
    }

    public List<Rule> getRules() {
        return rules;
    }

    public List<NotificationRule> getNotificationRules() {
        return notificationRules;
    }










}
