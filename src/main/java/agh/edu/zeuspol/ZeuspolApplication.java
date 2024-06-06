package agh.edu.zeuspol;

import agh.edu.zeuspol.datastructures.NotificationRule;
import agh.edu.zeuspol.datastructures.Rule;
import agh.edu.zeuspol.parsers.RuleJsonParser;
import agh.edu.zeuspol.services.HephaestusQueryService;
import io.github.hephaestusmetrics.model.metrics.Metric;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class ZeuspolApplication {

	public static void main(String[] args) {
		//TODO -> correct startup
		ConfigurableApplicationContext context = SpringApplication.run(ZeuspolApplication.class, args);

		String jsonString = "[" +
				"{\"attribute\": UPTIME, \"subject\": CPU, \"value\": [3, 5], \"unit\": NUMBER, \"action\": BT}, " +
				"{\"attribute\": RESOURCE, \"subject\": STORAGE, \"value\": [3], \"unit\": PERCENT, \"action\": LT, \"email\": filip@filip.com}, " +
				"{\"attribute\": RESOURCE, \"subject\": RAM, \"value\": [3], \"unit\": KILOBYTE, \"action\": EQ}" +
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


		HephaestusQueryService metricsService = context.getBean(HephaestusQueryService.class);
		while(true) {
			List<Metric> metrics = metricsService.getMetrics();
			System.out.println("=============================================");
			System.out.println("METRICS:");
			for(Metric metric : metrics) {
				System.out.println("name: " + metric.name + ", value: " + metric.value);
			}
			System.out.println("=============================================");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

	}

}
