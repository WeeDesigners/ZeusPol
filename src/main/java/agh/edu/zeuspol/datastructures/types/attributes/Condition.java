package agh.edu.zeuspol.datastructures.types.attributes;

public class Condition {
    public final String metric;
    public final RelationType relation;
    public final double value;

    public Condition(String metric, RelationType relation, double value) {
        this.metric = metric;
        this.relation = relation;
        this.value = value;
    }

    @Override
    public String toString() {
        return "{" + metric + ", " + relation + ", " + value + "}";
    }
}
