package agh.edu.zeuspol.drools;

import agh.edu.zeuspol.exceptions.DynamicDrlBuildError;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DynamicDrlBuilderAndExecutorTest {

  @Test
  public void dynamicLoaderTest() {
    String toAppend = "test";

    String drlFileContent =
        """
            package com.example;

            import java.util.List;

            rule "Add element if list has more than one item"
                when
                    $sb : StringBuilder(this.toString() == "");
                then
                    $sb.append("%s");
            end
        """
            .formatted(toAppend);

    DynamicDrlBuilder builder = new DynamicDrlBuilder();

    builder.addFile("test/testModify.drl", drlFileContent);
    DrlRuleExecutor executor = builder.build();

    StringBuilder sb = new StringBuilder();

    executor.fireRules(List.of(sb));
    Assertions.assertEquals(toAppend, sb.toString());
  }

  @Test
  public void multipleBuildsTest() {
    String toAppend = "test";

    String drlFileContent =
        """
            package com.example;

            import java.util.List;

            rule "Add element if list has more than one item"
                when
                    $sb : StringBuilder(this.toString() == "");
                then
                    $sb.append("%s");
            end
        """
            .formatted(toAppend);

    DynamicDrlBuilder loader1 = new DynamicDrlBuilder();
    DynamicDrlBuilder loader2 = new DynamicDrlBuilder();

    StringBuilder fact1 = new StringBuilder();
    StringBuilder fact2 = new StringBuilder();

    loader1.addFile("test/test.drl", drlFileContent.getBytes(StandardCharsets.UTF_8));
    DrlRuleExecutor executor1 = loader1.build();

    loader2.addFile("test/test.drl", drlFileContent.getBytes(StandardCharsets.UTF_8));
    DrlRuleExecutor executor2 = loader2.build();

    executor2.fireRules(List.of(fact2));
    executor1.fireRules(List.of(fact1));

    Assertions.assertEquals(toAppend, fact1.toString());
    Assertions.assertEquals(toAppend, fact2.toString());
  }

  @Test
  public void removeFileTest() {
    String toAppend = "test";

    String drlFileContent =
        """
            package com.example;

            import java.util.List;

            rule "Add element if list has more than one item"
                when
                    $sb : StringBuilder(this.toString() == "");
                then
                    $sb.append("%s");
            end
        """
            .formatted(toAppend);

    DynamicDrlBuilder loader1 = new DynamicDrlBuilder();
    DynamicDrlBuilder loader2 = new DynamicDrlBuilder();

    StringBuilder fact1 = new StringBuilder();
    StringBuilder fact2 = new StringBuilder();

    loader1.addFile("test/test.drl", drlFileContent.getBytes(StandardCharsets.UTF_8));
    loader1.removeFile("test/test.drl");
    DrlRuleExecutor executor1 = loader1.build();

    loader2.addFile("test/test.drl", drlFileContent.getBytes(StandardCharsets.UTF_8));
    loader2.removeFile("test/test.drl");
    DrlRuleExecutor executor2 = loader2.build();

    executor2.fireRules(List.of(fact2));
    executor1.fireRules(List.of(fact1));

    Assertions.assertEquals("", fact1.toString());
    Assertions.assertEquals("", fact2.toString());
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

    Assertions.assertEquals(drlFileContentPassed, drlFileContentReceived);
  }

  @Test
  public void buildErrorTest() {
    String invalidDrlFileContent =
        """
        package com.example;
        someGibrish;alkjdsf;

        import java.util.List;

        rule "Add element if list has more than one item"
            when
                $sb : StringBuilder(this.toString() == "");
            then
                $sb.append("%s");
        end
        """;

    DynamicDrlBuilder builder = new DynamicDrlBuilder();

    builder.addFile("test/testModify.drl", invalidDrlFileContent);

    Assertions.assertThrows(DynamicDrlBuildError.class, builder::build);
  }
}
