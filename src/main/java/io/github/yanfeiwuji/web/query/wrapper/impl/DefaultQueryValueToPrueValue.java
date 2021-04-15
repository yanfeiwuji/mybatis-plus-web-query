package io.github.yanfeiwuji.web.query.wrapper.impl;

import io.github.yanfeiwuji.web.query.QueryConst;
import io.github.yanfeiwuji.web.query.QueryRuleEnum;
import io.github.yanfeiwuji.web.query.wrapper.QueryValueToPrueValue;

import java.util.Arrays;

/**
 * @author yanfeiwuji
 * @date 2021/4/15 9:48 上午
 */
public class DefaultQueryValueToPrueValue implements QueryValueToPrueValue {

  @Override
  public Object[] convert(String value) {

    String currentValue = value;
    currentValue = handlerOr(currentValue);
    currentValue = handlerStart(currentValue);
    currentValue = handlerEnd(currentValue);
    return handlerMult(currentValue);
  }


  @Override
  public String handlerOr(String value) {
    String orMark = ruleValueBlank(QueryConst.OR_MARK);
    if (value.startsWith(orMark)) {
      return value.replace(orMark, "");
    }
    return value;
  }

  @Override
  public String handlerStart(String value) {
    final boolean present = Arrays.stream(QueryRuleEnum.values())
      .map(QueryRuleEnum::getCondition)
      .map(this::ruleValueBlank)
      .anyMatch(value::startsWith);
    if (present) {
      return value.substring(2);
    }
    String needValue = value;
    if (value.startsWith(QueryConst.NOT_MARKER)) {
      needValue = value.substring(1);
    }
    if (needValue.startsWith(QueryConst.LIKE)) {
      needValue = value.substring(1);
    }

    return needValue;
  }

  @Override
  public String handlerEnd(String value) {
    if (value.endsWith(QueryConst.LIKE)) {
      return value.substring(0, value.length() - 1);
    }
    return value;
  }

  @Override
  public Object[] handlerMult(String value) {
    if (value.startsWith(QueryConst.lEFT_BRACKET) && value.endsWith(QueryConst.RIGHT_BRACKET)) {
      return new Object[]{value.substring(1, value.length() - 1)};
    } else {
      return value.split(QueryConst.COMMA);
    }
  }

  private String ruleValueBlank(String str) {
    return str + " ";
  }

}
