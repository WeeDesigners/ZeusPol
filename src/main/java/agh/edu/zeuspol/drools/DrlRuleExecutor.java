package agh.edu.zeuspol.drools;

import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import java.util.List;

public class DrlRuleExecutor {
    private final KieBase kieBase;

    public DrlRuleExecutor(KieBase kieBase) {
        this.kieBase = kieBase;
    }

    public void fireRules(List<Object> facts) {
        KieSession session = this.kieBase.newKieSession();
        if (facts != null){
            for (Object o: facts) {
                session.insert(o);
            }
        }
        session.fireAllRules();
    }
}
