package agh.edu.zeuspol.drools.builder.impl;

import agh.edu.zeuspol.drools.builder.base.DrlNameBuilder;

public class SimpleDrlNameBuilder extends DrlNameBuilder {

    private String name;

    public SimpleDrlNameBuilder(String name) {
        this.name = name;
    }

    @Override
    public String build() {
        return this.name;
    }
}
