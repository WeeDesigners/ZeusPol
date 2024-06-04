package agh.edu.zeuspol;

import agh.edu.zeuspol.data_structures.NotificationRule;
import agh.edu.zeuspol.data_structures.Rule;
import agh.edu.zeuspol.parsers.RuleJsonParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.management.Notification;
import java.util.List;

@SpringBootApplication
public class ZeuspolApplication {

	public static void main(String[] args) {
		//TODO -> correct startup
//		SpringApplication.run(ZeuspolApplication.class, args);

		String jsonString = "[" +
				"{\"attribute\": UPTIME, \"subject\": CPU, \"value\": [3, 5], \"unit\": NUMBER, \"action\": BT}, " +
				"{\"attribute\": RESOURCE, \"subject\": STORAGE, \"value\": [3], \"unit\": PERCENT, \"action\": LT, \"email\": filip@filip.com}, " +
				"{\"attribute\": RESOURCE, \"subject\": RAM, \"value\": [3], \"unit\": KILOBYTES, \"action\": EQ}" +
				"]";

		RuleJsonParser parser = new RuleJsonParser(jsonString);
		parser.parseJson();
		List<Rule> rules = parser.getRules();
		List<NotificationRule> notificationRules = parser.getNotificationRules();

		System.out.println("=============================================");
		System.out.println("RULES:");
		System.out.println(rules);
		System.out.println("=============================================");
		System.out.println("NOTIFICATION RULES:");
		System.out.println(notificationRules);

	}

}
