package agh.edu.zeuspol.iofile;

import agh.edu.zeuspol.datastructures.NotificationRule;
import agh.edu.zeuspol.datastructures.Rule;
import agh.edu.zeuspol.parsers.RuleJsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JSONLoader {

    private final String jsonString;

    public JSONLoader(String path) throws IOException {
        jsonString = Files.readString(Path.of(path));
    }

    public String getJsonString() {
        return jsonString;
    }

    public List<Rule> getRules(){
        RuleJsonParser rjp = new RuleJsonParser(jsonString);
        rjp = rjp.parseJson();
        return rjp.getRules();
    }

    public List<NotificationRule> getNotificationRules(){
        RuleJsonParser rjp = new RuleJsonParser(jsonString);
        rjp = rjp.parseJson();
        return rjp.getNotificationRules();
    }


}
