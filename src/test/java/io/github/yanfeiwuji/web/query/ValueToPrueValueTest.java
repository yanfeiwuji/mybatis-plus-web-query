package io.github.yanfeiwuji.web.query;

import io.github.yanfeiwuji.web.query.wrapper.impl.DefaultQueryValueToPrueValue;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author yanfeiwuji
 * @date 2021/4/15 4:27 下午
 */
public class ValueToPrueValueTest {

  DefaultQueryValueToPrueValue covert = new DefaultQueryValueToPrueValue();

  String prueValue = "123";

  @Test
  public void testOr() {
    String c1 = "or (" + prueValue + ")";
    String c2 = "or " + prueValue;
    allAssert(c1, c2);

  }

  @Test
  public void testNot() {
    String not1 = "!(" + prueValue + ")";
    String not2 = "!" + prueValue;
  }

  @Test
  public void testLike() {
    String leftLike = "*" + prueValue;
    String rightLike = prueValue + "*";
    String like = "*" + prueValue + "*";
    allAssert(leftLike, rightLike, like);
  }

  @Test
  public void testRule() {
    final List<String> collect = Arrays.stream(QueryRuleEnum.values()).map(QueryRuleEnum::getCondition).map(s -> s + " " + prueValue)
      .collect(Collectors.toList());
    final String[] strings = collect.toArray(new String[]{});
    allAssert(strings);
  }


  private void allAssert(String... values) {
    Arrays.stream(values).map(covert::convert)
      .forEach(s -> {
        assert s[0].toString().equals(prueValue);
      });
  }
}
