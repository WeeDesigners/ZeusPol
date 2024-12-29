package agh.edu.zeuspol.drools.builder.base;

import agh.edu.zeuspol.drools.builder.DrlBuilder;
import java.util.ArrayList;
import java.util.List;

public class DrlImportsBuilder implements DrlBuilder {

  private List<String> importsList = new ArrayList<>();

  public String build() {
    StringBuilder imports = new StringBuilder();
    for (String importString : importsList) {
      imports.append(importString).append("\n");
    }
    return imports.toString() + "\n";
  }

  public void addImport(String newImport) {
    this.importsList.add(newImport);
  }

  public void addImports(List<String> newImports) {
    this.importsList.addAll(newImports);
  }

  public void removeImport(int i) {
    this.importsList.remove(i);
  }
}
