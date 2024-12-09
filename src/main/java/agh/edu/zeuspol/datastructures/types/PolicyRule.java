package agh.edu.zeuspol.datastructures.types;

import agh.edu.zeuspol.datastructures.types.attributes.*;

import java.util.Objects;

public class PolicyRule {

  public final long id;
  public final String name;
  public final String metric;
  public final RelationType relation;
  public final double value;
  public final ExecutionRequest executionRequest;

  public PolicyRule(long id, String name, String metric, RelationType relation, double value, ExecutionRequest executionRequest) {
    this.id = id;
    this.name = name;
    this.metric = metric;
    this.relation = relation;
    this.value = value;
    this.executionRequest = executionRequest;
  }


  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    PolicyRule that = (PolicyRule) object;
    return id == that.id && Double.compare(value, that.value) == 0 && Objects.equals(name, that.name) && Objects.equals(metric, that.metric) && relation == that.relation && Objects.equals(executionRequest, that.executionRequest);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, metric, relation, value, executionRequest);
  }

  @Override
  public String toString() {
    return "PolicyRule{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", metric='" + metric + '\'' +
            ", relation=" + relation +
            ", value=" + value +
            ", params=" + executionRequest +
            '}';
  }
}
