package agh.edu.zeuspol;

import agh.edu.zeuspol.iofile.JSONLoader;
import agh.edu.zeuspol.services.HephaestusQueryService;
import io.github.hephaestusmetrics.model.metrics.Metric;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class ZeuspolApplication {

	private static ConfigurableApplicationContext context;
	private static boolean isRunning = false;

	public static void main(String[] args) throws IOException {

		context = SpringApplication.run(ZeuspolApplication.class, args);


		//TEST RULES

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

	public static boolean isRunning(){
		return isRunning;
	}


	public static void mainLoop(){
		HephaestusQueryService metricsService = context.getBean(HephaestusQueryService.class);
		isRunning = true;
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
				isRunning = false;
				throw new RuntimeException(e);
			}
		}
	}

}
