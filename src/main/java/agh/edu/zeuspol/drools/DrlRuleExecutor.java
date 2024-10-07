package agh.edu.zeuspol.drools;

import java.util.List;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;

public class DrlRuleExecutor {
  private final KieBase kieBase;

  public DrlRuleExecutor(KieBase kieBase) {
    this.kieBase = kieBase;
  }

  public void fireRules(List<Object> facts) {
    KieSession session = this.kieBase.newKieSession();
    if (facts != null) {
      for (Object o : facts) {
        session.insert(o);
      }
    }
    session.fireAllRules();
  }
}
