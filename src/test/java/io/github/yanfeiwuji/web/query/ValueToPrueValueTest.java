package io.github.yanfeiwuji.web.query;

import cn.hutool.json.JSONUtil;
import io.github.yanfeiwuji.web.query.wrapper.impl.DefaultQueryValueToPrueValue;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yanfeiwuji
   2021/4/15 4:27 下午
 */
public class ValueToPrueValueTest {

  DefaultQueryValueToPrueValue convert = new DefaultQueryValueToPrueValue();

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
    allAssert(not1, not2);
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
    final List<String> collect = Arrays.stream(QueryRuleEnum.values()).map(QueryRuleEnum::getCondition)
      .map(s -> s + " " + prueValue)
      .collect(Collectors.toList());
    final String[] strings = collect.toArray(new String[]{});
    allAssert(strings);
  }

  @Test
  public void testCom() {
    String v1 = "or gt !1234 23 435";
    String v2 = "or !(234)";
    String v3 = "or !234";
    assert convert.convert(v1)[0].toString().equals("!1234 23 435");
    assert convert.convert(v2)[0].toString().equals("234");
    assert convert.convert(v3)[0].toString().equals("234");

  }

  private void allAssert(String... values) {
    Arrays.stream(values).map(convert::convert)
      .forEach(s -> {
        assert s[0].toString().equals(prueValue);
      });
  }
}
