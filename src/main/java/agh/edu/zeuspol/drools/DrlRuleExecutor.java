package agh.edu.zeuspol.drools;

import java.util.List;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;

public class DrlRuleExecutor {
  private final KieBase kieBase;
  private KieSession kieSession;

  public DrlRuleExecutor(KieBase kieBase) {
    this.kieBase = kieBase;
    this.kieSession = kieBase.newKieSession();
  }

  public void fireRules(List<Object> facts) {
    if (facts != null) {
      for (Object o : facts) {
        this.kieSession.insert(o);
      }
    }
    this.kieSession.fireAllRules();
    this.kieSession.dispose();
    this.kieSession = this.kieBase.newKieSession();
  }
}
