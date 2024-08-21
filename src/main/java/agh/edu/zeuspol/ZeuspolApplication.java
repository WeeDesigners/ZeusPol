package agh.edu.zeuspol;
import agh.edu.zeuspol.drools.DroolsClass;
import agh.edu.zeuspol.iofile.JSONLoader;
import agh.edu.zeuspol.services.HephaestusQueryService;
import agh.edu.zeuspol.services.ThemisService;
import io.github.hephaestusmetrics.model.metrics.Metric;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@SpringBootApplication
public class ZeuspolApplication {

	private static ConfigurableApplicationContext context;
	private static boolean isRunning = false;
	private static final Lock lock = new ReentrantLock();
	private static final Condition condition = lock.newCondition();

	public static void main(String[] args) throws IOException {

		context = SpringApplication.run(ZeuspolApplication.class, args);


		//TEST RULES

		String path = "SlaFile.json";
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


		//run loop of main loops
		mainLoop();

	}

	public static boolean isRunning(){
		return isRunning;
	}

	public static void startApp(){
		lock.lock();
		isRunning = true;
		condition.signalAll();
		lock.unlock();
	}

	public static void stopApp(){
		lock.lock();
		isRunning = false;
		condition.signalAll();
		lock.unlock();
	}

	private static void waitForCondition() throws InterruptedException {
		lock.lock();
		try {
			while (!isRunning) {
				condition.await();
			}
			// Proceed when conditionMet becomes true
		} finally {
			lock.unlock();
		}
	}

	private static void mainLoop() {
		HephaestusQueryService metricsService = context.getBean(HephaestusQueryService.class);
		ThemisService themisService = context.getBean(ThemisService.class);
		DroolsClass drools = new DroolsClass("ZeusSession");

		drools.fire(null);
		//infinite loop of mainLoops
		while(true){
		//if app should be running, then run main loop
			try {
				waitForCondition();
			} catch (InterruptedException e){
				continue;
			}

			List<Metric> metrics = metricsService.getMetrics();
			System.out.println("=============================================");
			System.out.println("METRICS:");
			for(Metric metric : metrics) {
				System.out.println("name: " + metric.name + ", value: " + metric.value);
//				drools.fire(metric);
			}

			System.out.println("=============================================");

//			System.out.println("ACTIONS:");
			System.out.println(themisService.getActions());
			System.out.println("=============================================");
			//wait some time
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

	}


}
