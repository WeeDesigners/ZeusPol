package agh.edu.zeuspol.drools.builder.base;

import agh.edu.zeuspol.drools.builder.DrlBuilder;

import java.util.List;

public class DrlActionBuilder implements DrlBuilder {

    public String build() {
        return "\n";
    }

    public List<String> imports(){
        return List.of();
    }
}
