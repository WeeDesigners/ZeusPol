package agh.edu.zeuspol;

import agh.edu.zeuspol.datastructures.storage.Policies;
import agh.edu.zeuspol.datastructures.storage.Sla;
import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.datastructures.types.attributes.*;
import agh.edu.zeuspol.drools.*;
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

    // load Sla and Policies from Hermes
    HermesService hermesService = context.getBean(HermesService.class);
    Policies.setInstance(hermesService.getPolicies());
    Sla.setInstance(hermesService.getSla());

    // check if SLA and Policies are imported properly
    System.out.println(Sla.getInstance());
    System.out.println(Policies.getInstance());

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

    RuleToDrlConverter converter = new RuleToDrlConverter();

    Params b = new Params();
    b.put("actionName", "ChangeResourcesOfContainerWithinDeploymentAction");
    b.put("collectionName", "kubernetes");
    b.put("namespace", "test-app");
    b.put("deploymentName", "test-app");
    b.put("containerName", "test-app");
    b.put("limitsCpu", "2");
    b.put("limitsMemory", "800Mi");
    b.put("requestsCpu", "2");
    b.put("requestsMemory", "800Mi");

    PolicyRule rule =
        new PolicyRule(
            RuleAttribute.RESOURCE,
            RuleSubject.CPU,
            List.of(10),
            UnitType.PERCENT,
            RelationType.GT,
            Action.KubernetesChangeResourcesOfContainerWithinDeploymentAction,
            b);

    DrlStringFile s = converter.convert(rule);
    System.out.println(s);

    DynamicDrlBuilder builder = new DynamicDrlBuilder();

    builder.addFile(s);

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
