package agh.edu.zeuspol;

import agh.edu.zeuspol.drools.DrlRuleExecutor;
import agh.edu.zeuspol.drools.DynamicDrlBuilder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DynamicDrlBuilderTest {

  @Test
  public void dynamicLoaderTest() {
    String s1 =
        """
            package com.example;

            import java.util.List;

            rule "Add element if list has more than one item"
                when
                    $list : List(size > 1)
                then
                    $list.add("NewElement");
            end
        """;

    DynamicDrlBuilder builder = new DynamicDrlBuilder();

    builder.addFile("test/testModify.drl", s1);
    DrlRuleExecutor executor = builder.build();

    List<String> fact = new ArrayList<>();
    fact.add("hey");
    fact.add("hello");

    executor.fireRules(List.of(fact));
    assert fact.size() == 3;
  }

  @Test
  public void multipleBuildsTest() {
    DynamicDrlBuilder loader1 = new DynamicDrlBuilder();
    DynamicDrlBuilder loader2 = new DynamicDrlBuilder();

    String drlFileContent =
        """
            package com.example;

            import java.util.List;

            rule "Add element if list has more than one item"
                when
                    $list : List(size > 1)
                then
                    $list.add("NewElement");
            end
        """;

    List<String> fact1 = new ArrayList<>();
    fact1.add("hey");
    fact1.add("hello");

    List<String> fact2 = new ArrayList<>();
    fact2.add("hey");
    fact2.add("hello");
    fact2.add("hello1");

    loader1.addFile("test/test.drl", drlFileContent.getBytes(StandardCharsets.UTF_8));
    DrlRuleExecutor executor1 = loader1.build();

    loader2.addFile("test/test.drl", drlFileContent.getBytes(StandardCharsets.UTF_8));
    DrlRuleExecutor executor2 = loader2.build();

    executor2.fireRules(List.of(fact2));
    executor1.fireRules(List.of(fact1));

    assert fact1.size() == 3;
    assert fact2.size() == 4;
  }

  @Test
  public void removeFileTest() {
    DynamicDrlBuilder loader1 = new DynamicDrlBuilder();
    DynamicDrlBuilder loader2 = new DynamicDrlBuilder();

    String drlFileContent =
        """
            package com.example;

            import java.util.List;

            rule "Add element if list has more than one item"
                when
                    $list : List(size > 1)
                then
                    $list.add("NewElement");
            end
        """;

    List<String> fact1 = new ArrayList<>();
    fact1.add("hey");
    fact1.add("hello");

    List<String> fact2 = new ArrayList<>();
    fact2.add("hey");
    fact2.add("hello");
    fact2.add("hello1");

    loader1.addFile("test/test.drl", drlFileContent.getBytes(StandardCharsets.UTF_8));
    loader1.removeFile("test/test.drl");
    DrlRuleExecutor executor1 = loader1.build();

    loader2.addFile("test/test.drl", drlFileContent.getBytes(StandardCharsets.UTF_8));
    loader2.removeFile("test/test.drl");
    DrlRuleExecutor executor2 = loader2.build();

    executor2.fireRules(List.of(fact2));
    executor1.fireRules(List.of(fact1));

    assert fact1.size() == 2;
    assert fact2.size() == 3;
  }

  @Test
  public void getFileTest() {
    DynamicDrlBuilder loader = new DynamicDrlBuilder();

    String drlFileContentPassed =
        """
            package com.example;

            import java.util.List;

            rule "Add element if list has more than one item"
                when
                    $list : List(size > 1)
                then
                    $list.add("NewElement");
            end
        """;

    loader.addFile("test/test.drl", drlFileContentPassed.getBytes(StandardCharsets.UTF_8));

    String drlFileContentReceived = loader.getFile("test/test.drl");

    assert drlFileContentPassed.equals(drlFileContentReceived);
  }
}
