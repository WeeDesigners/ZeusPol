package agh.edu.zeuspol.drools;

import agh.edu.zeuspol.datastructures.Rule;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DroolsClass {
    private final KieServices kieServices = KieServices.Factory.get();

    private String rulesDirectory;

    public DroolsClass(String rulesDirectory){
        this.rulesDirectory = rulesDirectory;
    }

    private KieFileSystem getKieFileSystem() {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        List<String> rules = List.of(this.rulesDirectory);
        for (String rule : rules) {
            kieFileSystem.write(ResourceFactory.newClassPathResource(rule));
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(this.rulesDirectory))) {
            for (Path path : stream) {
                kieFileSystem.write(ResourceFactory.newClassPathResource(path.toString()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return kieFileSystem;
    }

    public KieSession getKieSession() {
        KieBuilder kb = kieServices.newKieBuilder(getKieFileSystem());
        kb.buildAll();

        KieRepository kieRepository = kieServices.getRepository();
        ReleaseId krDefaultReleaseId = kieRepository.getDefaultReleaseId();
        KieContainer kieContainer = kieServices.newKieContainer(krDefaultReleaseId);

        return kieContainer.newKieSession();
    }

    public void fire(Rule rule){
        KieSession kieSession = this.getKieSession();
        kieSession.insert(rule);
        kieSession.fireAllRules();
    }
}
