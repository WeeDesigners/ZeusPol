package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.types.attributes.Action;
import java.util.List;

public abstract class ThemisActionBuilder {

  public abstract String buildThemisAction(Action action);

  public abstract List<String> importsNeeded();
}
