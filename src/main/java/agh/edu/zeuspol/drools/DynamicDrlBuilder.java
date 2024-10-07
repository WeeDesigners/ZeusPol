package agh.edu.zeuspol.drools;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class DynamicDrlBuilder {

    private String defaultRulesPath = "src/main/resources/";
    private KieFileSystem kieFileSystem;

    public DynamicDrlBuilder() {
        KieServices kieServices = KieServices.get();
        KieModuleModel kieModuleModel = kieServices.newKieModuleModel();

        KieBaseModel kieBaseModel1 = kieModuleModel.newKieBaseModel( "KBase1 ")
                .setDefault( true );

        KieSessionModel ksessionModel1 = kieBaseModel1.newKieSessionModel( "KSession1" )
                .setDefault( true );

        this.kieFileSystem = kieServices.newKieFileSystem();
        this.kieFileSystem.writeKModuleXML(kieModuleModel.toXML());
    }

    public void addFile(String fileSystemPath, byte[] data) {
        if (this.kieFileSystem.read(this.fullFileSystemPath(fileSystemPath)) != null) {
            throw new RuntimeException("File in this path already exists!");
        }

        Resource resource = KieServices.get().getResources().newByteArrayResource(data)
                .setTargetPath(this.fullFileSystemPath(fileSystemPath))
                .setResourceType(ResourceType.DRL);
        this.kieFileSystem.write(resource);
    }

    public void addFile(String fileSystemPath, String fileContent) {
        this.addFile(fileSystemPath, fileContent.getBytes(StandardCharsets.UTF_8));
    }

    public String getFile(String fileSystemPath) {
        byte[] fileBytes = this.kieFileSystem.read(this.fullFileSystemPath(fileSystemPath));
        if (fileBytes == null) {
            throw new RuntimeException("File in this path does not exist!");
        }
        return new String(fileBytes, StandardCharsets.UTF_8);
    }

    public void removeFile(String fileSystemPath) {
        if (this.kieFileSystem.read(this.fullFileSystemPath(fileSystemPath)) == null) {
            throw new RuntimeException("File in this path does not exist!");
        }
        this.kieFileSystem.delete(this.fullFileSystemPath(fileSystemPath));
    }

    public DrlRuleExecutor build() {
        KieServices kieServices = KieServices.Factory.get();
        KieBuilder kieBuilder = kieServices.newKieBuilder(this.kieFileSystem).buildAll();
        List<Message> message = kieBuilder.getResults().getMessages(Message.Level.ERROR);
        if (kieBuilder.getResults().getMessages(Message.Level.ERROR).size() != 0){
            System.out.println("While building file system errors occurred!");
            System.out.println(message);
            throw new RuntimeException("While building file system errors occurred!");
        }

        KieContainer kieContainer  = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        return new DrlRuleExecutor(kieContainer.getKieBase());
    }

    private String fullFileSystemPath(String fileSystemPath) {
        return this.defaultRulesPath + fileSystemPath;
    }
}
