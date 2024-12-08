package agh.edu.zeuspol.datastructures.types;

import agh.edu.zeuspol.datastructures.types.attributes.*;
import agh.edu.zeuspol.datastructures.types.base.Rule;
import java.util.List;
import java.util.Objects;

public class PolicyRule {

  public final long id;
  public final String name;
  public final String metric;
  public final RelationType relation;
  public final double value;
  public final ExecutionRequest params;

  public PolicyRule(long id, String name, String metric, RelationType relation, double value, ExecutionRequest params) {
    this.id = id;
    this.name = name;
    this.metric = metric;
    this.relation = relation;
    this.value = value;
    this.params = params;
  }


  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    PolicyRule that = (PolicyRule) object;
    return id == that.id && Double.compare(value, that.value) == 0 && Objects.equals(name, that.name) && Objects.equals(metric, that.metric) && relation == that.relation && Objects.equals(params, that.params);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, metric, relation, value, params);
  }

  @Override
  public String toString() {
    return "PolicyRule{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", metric='" + metric + '\'' +
            ", relation=" + relation +
            ", value=" + value +
            ", params=" + params +
            '}';
  }
}
