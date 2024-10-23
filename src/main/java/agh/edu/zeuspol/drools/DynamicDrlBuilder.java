package agh.edu.zeuspol.drools;

import agh.edu.zeuspol.exceptions.DynamicDrlBuildError;
import java.nio.charset.StandardCharsets;
import java.util.List;
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

public class DynamicDrlBuilder {

  private final String defaultRulesPath = "src/main/resources/";
  private final KieFileSystem kieFileSystem;

  public DynamicDrlBuilder() {
    KieServices kieServices = KieServices.get();
    KieModuleModel kieModuleModel = kieServices.newKieModuleModel();

    KieBaseModel kieBaseModel1 = kieModuleModel.newKieBaseModel("KBase1 ").setDefault(true);

    KieSessionModel ksessionModel1 = kieBaseModel1.newKieSessionModel("KSession1").setDefault(true);

    this.kieFileSystem = kieServices.newKieFileSystem();
    this.kieFileSystem.writeKModuleXML(kieModuleModel.toXML());
  }

  public void addFile(String fileSystemPath, byte[] data) {
    if (this.kieFileSystem.read(this.fullFileSystemPath(fileSystemPath)) != null) {
      throw new RuntimeException("File in this path already exists!");
    }

    Resource resource =
        KieServices.get()
            .getResources()
            .newByteArrayResource(data)
            .setTargetPath(this.fullFileSystemPath(fileSystemPath))
            .setResourceType(ResourceType.DRL);
    this.kieFileSystem.write(resource);
  }

  public void addFile(String fileSystemPath, String fileContent) {
    this.addFile(fileSystemPath, fileContent.getBytes(StandardCharsets.UTF_8));
  }

  public void addFile(DrlStringFile stringFile) {
    this.addFile(stringFile.getPath(), stringFile.getFileContent());
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

  public DrlRuleExecutor build() throws DynamicDrlBuildError {
    KieServices kieServices = KieServices.Factory.get();
    KieBuilder kieBuilder = kieServices.newKieBuilder(this.kieFileSystem).buildAll();
    List<Message> messages = kieBuilder.getResults().getMessages(Message.Level.ERROR);
    if (!messages.isEmpty()) {
      throw new DynamicDrlBuildError(messages);
    }

    KieContainer kieContainer =
        kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
    return new DrlRuleExecutor(kieContainer.getKieBase());
  }

  //  Helper methods
  private String fullFileSystemPath(String fileSystemPath) {
    return this.defaultRulesPath + fileSystemPath;
  }
}
