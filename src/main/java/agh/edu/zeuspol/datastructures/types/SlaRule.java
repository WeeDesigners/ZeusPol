package agh.edu.zeuspol.datastructures.types;

import agh.edu.zeuspol.datastructures.types.attributes.*;
import agh.edu.zeuspol.datastructures.types.base.Rule;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SlaRule {

  public final String valueType; // TODO - maybe ENUM ?
  public final String metric;
  public final RelationType relation;
  public final double value;

  public SlaRule(String valueType, String metric, RelationType relation, double value) {
    this.valueType = valueType;
    this.metric = metric;
    this.relation = relation;
    this.value = value;
  }

}
