package agh.edu.zeuspol;

import agh.edu.zeuspol.datastructures.storage.Policies;
import agh.edu.zeuspol.datastructures.storage.Sla;
import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.datastructures.types.SlaRule;
import agh.edu.zeuspol.drools.*;
import agh.edu.zeuspol.drools.converter.PolicyRuleToDrlConverter;
import agh.edu.zeuspol.drools.converter.SlaRuleToDrlConverter;
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

    // TODO -> idk where to place it
    HermesService hermesService = context.getBean(HermesService.class);
    Policies myS3xiPolicies = hermesService.getPolicies();
    List<Sla> myS3xiSlas = hermesService.getAllSlas();

    // checking if everything works properly
    System.out.println();
    System.out.println("Policies from Hermes:");
    System.out.println(myS3xiPolicies.toString());
    System.out.println();
    System.out.println("Slas from Hermes:");
    System.out.println(myS3xiSlas.toString());
    System.out.println();
    // end of TODO

    PolicyRuleToDrlConverter policyConverter = new PolicyRuleToDrlConverter();
    SlaRuleToDrlConverter slaRuleConverter = new SlaRuleToDrlConverter();
    DynamicDrlBuilder builder = new DynamicDrlBuilder();

    System.out.println("--------------Policies:--------------");
    for (PolicyRule pr : Policies.getInstance().getRules()) {
      System.out.println(pr);
      builder.addFile(policyConverter.convert(pr));
    }

    System.out.println("--------------Sla rules:--------------");
    for (Sla sla : myS3xiSlas) {
      for (SlaRule slaRule : sla.getRules()) {
        System.out.println(slaRule);
        builder.addFile(slaRuleConverter.convert(sla, slaRule));
      }
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
      objs.addAll(Policies.getInstance().getRulesStatsList());

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
