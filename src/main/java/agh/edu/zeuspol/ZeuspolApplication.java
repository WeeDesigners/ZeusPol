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

	public static void main(String[] args) throws IOException {
		//TODO -> correct startup
		ConfigurableApplicationContext context = SpringApplication.run(ZeuspolApplication.class, args);


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
