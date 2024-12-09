package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.types.attributes.ExecutionRequest;

import java.util.List;

public abstract class ThemisActionBuilder {

  public abstract String buildThemisAction(ExecutionRequest executionRequest);

  public abstract List<String> importsNeeded();
}
