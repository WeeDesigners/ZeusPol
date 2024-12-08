package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.types.attributes.Action;
import agh.edu.zeuspol.datastructures.types.attributes.ExecutionRequest;
import agh.edu.zeuspol.datastructures.types.attributes.Params;
import java.util.List;

public abstract class ThemisActionBuilder {

  public abstract String buildThemisAction(ExecutionRequest executionRequest);

  public abstract List<String> importsNeeded();
}
