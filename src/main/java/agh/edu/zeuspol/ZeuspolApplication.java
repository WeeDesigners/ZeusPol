package agh.edu.zeuspol;

import agh.edu.zeuspol.datastructures.storage.Policies;
import agh.edu.zeuspol.datastructures.storage.Sla;
import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.datastructures.types.attributes.*;
import agh.edu.zeuspol.drools.*;
import agh.edu.zeuspol.drools.converter.HttpClientThemisActionBuilder;
import agh.edu.zeuspol.drools.converter.RuleToDrlConverter;
import agh.edu.zeuspol.services.HephaestusQueryService;
import agh.edu.zeuspol.services.HermesService;
import agh.edu.zeuspol.services.ThemisService;
import io.github.hephaestusmetrics.model.metrics.Metric;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ZeuspolApplication {

  private static ConfigurableApplicationContext context;
  private static boolean isRunning = false;
  private static final Lock lock = new ReentrantLock();
  private static final Condition condition = lock.newCondition();

  public static void main(String[] args) throws IOException {

    context = SpringApplication.run(ZeuspolApplication.class, args);
    // run loop of main loops
    mainLoop();
  }

  public static boolean isRunning() {
    return isRunning;
  }

  public static void startApp() {
    lock.lock();
    isRunning = true;
    condition.signalAll();
    lock.unlock();
  }

  public static void stopApp() {
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

    RuleToDrlConverter converter = new RuleToDrlConverter(new HttpClientThemisActionBuilder());

    ExecutionRequest b = new ExecutionRequest("kubernetes", "ChangeResourcesOfContainerWithinDeploymentAction");
    b.addParam("namespace", "test-app");
    b.addParam("deploymentName", "test-app");
    b.addParam("containerName", "test-app");
    b.addParam("limitsCpu", "2");
    b.addParam("limitsMemory", "800Mi");
    b.addParam("requestsCpu", "2");
    b.addParam("requestsMemory", "800Mi");


    PolicyRule rule = new PolicyRule(1, "ScaleKubernetesRule", "CPU", RelationType.GT, 0.5, b);



    DrlStringFile s = converter.convert(rule);
    System.out.println(s);

    DynamicDrlBuilder builder = new DynamicDrlBuilder();

    builder.addFile(s);

    for (PolicyRule pr: Policies.getInstance().getRules()) {
      System.out.println("----------------------------------");
      DrlStringFile sf = converter.convert(pr);
      System.out.println(sf.getFileContent());
      builder.addFile(sf);
    }

    DrlRuleExecutor executor = builder.build();

    // infinite loop of mainLoops
    while (true) {
      // if app should be running, then run main loop
      try {
        waitForCondition();
      } catch (InterruptedException e) {
        continue;
      }

      List<Metric> metrics = metricsService.getMetrics();
      List<Object> objs = new ArrayList<>();
      System.out.println("=============================================");
      System.out.println("METRICS:");
      for (Metric metric : metrics) {
        System.out.println("name: " + metric.getQueryTag() + ", value: " + metric.value);
        objs.add(metric);
      }
      executor.fireRules(objs);

      System.out.println("=============================================");

      //			System.out.println("ACTIONS:");
      System.out.println(themisService.getActions());
      System.out.println("=============================================");

      // wait some time
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
