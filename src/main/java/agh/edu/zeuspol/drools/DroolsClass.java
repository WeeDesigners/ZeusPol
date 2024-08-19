package agh.edu.zeuspol.drools;
import io.github.hephaestusmetrics.model.metrics.Metric;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


public class DroolsClass {
    private final KieServices kieServices = KieServices.get();
    private String sessionName;

    /**
     * @param  sessionName  can be found in resource/META-INF/kmodule.xml
     */
    public DroolsClass(String sessionName){
        this.sessionName = sessionName;
    }

    public KieSession getKieSession() {
        KieContainer kc = this.kieServices.getKieClasspathContainer();
        return kc.newKieSession(this.sessionName);
    }
    public void fire(Metric metric){
        KieSession kieSession = this.getKieSession();
        kieSession.insert(metric);
        kieSession.fireAllRules();
        kieSession.dispose();
    }
}
