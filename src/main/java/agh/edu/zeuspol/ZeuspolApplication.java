package agh.edu.zeuspol;

import agh.edu.zeuspol.datastructures.NotificationRule;
import agh.edu.zeuspol.datastructures.Rule;
import agh.edu.zeuspol.iofile.JSONLoader;
import agh.edu.zeuspol.parsers.RuleJsonParser;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class ZeuspolApplication {

	public static void main(String[] args) throws IOException {
		//TODO -> correct startup
//		SpringApplication.run(ZeuspolApplication.class, args);


		String path = "src/main/resources/SlaFile.json";
		JSONLoader jsonLoader = new JSONLoader(path);

		System.out.println("=================================================================================");
		System.out.println("Loading rules from " + path + " . . .");
		System.out.println("=================================================================================");
		System.out.println("Loaded file:");
		System.out.println(jsonLoader.getJsonString());
		System.out.println("=================================================================================");
		System.out.println("Loaded rules:");
		System.out.println(jsonLoader.getRules());
		System.out.println("=================================================================================");
		System.out.println("Loaded notification rules:");
		System.out.println(jsonLoader.getNotificationRules());
		System.out.println("=================================================================================");


	}

}
