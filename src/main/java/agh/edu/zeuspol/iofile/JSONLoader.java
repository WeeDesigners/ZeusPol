package agh.edu.zeuspol.iofile;

import agh.edu.zeuspol.datastructures.NotificationRule;
import agh.edu.zeuspol.datastructures.Rule;
import agh.edu.zeuspol.parsers.RuleJsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.core.io.ResourceLoader;

public class JSONLoader {

  private final String jsonString;

  public JSONLoader(String path) throws IOException {
    InputStream inputStream = ResourceLoader.class.getClassLoader().getResourceAsStream(path);
    if (inputStream == null) {
      throw new IllegalArgumentException("file not found! " + path);
    } else {
      jsonString = new String(inputStream.readAllBytes());
    }
  }

  public String getJsonString() {
    return jsonString;
  }

  public List<Rule> getRules() {
    RuleJsonParser rjp = new RuleJsonParser(jsonString);
    rjp = rjp.parseJson();
    return rjp.getRules();
  }

  public List<NotificationRule> getNotificationRules() {
    RuleJsonParser rjp = new RuleJsonParser(jsonString);
    rjp = rjp.parseJson();
    return rjp.getNotificationRules();
  }
}
