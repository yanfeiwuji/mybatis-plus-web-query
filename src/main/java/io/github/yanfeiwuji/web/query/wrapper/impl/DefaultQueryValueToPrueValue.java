package io.github.yanfeiwuji.web.query.wrapper.impl;

import cn.hutool.core.util.NumberUtil;
import io.github.yanfeiwuji.web.query.QueryConst;
import io.github.yanfeiwuji.web.query.QueryRuleEnum;
import io.github.yanfeiwuji.web.query.wrapper.QueryValueToPrueValue;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author yanfeiwuji
 * 2021/4/15 9:48 上午
 */
public class DefaultQueryValueToPrueValue implements QueryValueToPrueValue {

  @Override
  public Object[] convert(String value) {

    String currentValue = value;
    currentValue = handlerOr(currentValue);
    currentValue = handlerStart(currentValue);
    currentValue = handlerEnd(currentValue);

    final Object[] objects = handlerMult(currentValue);
    return Arrays.stream(objects)
      .filter(Objects::nonNull)
      .map(Object::toString)
      .map(s -> NumberUtil.isNumber(s) ? NumberUtil.parseLong(s) : s).toArray();
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

    // 多值不处理
    if (value.contains(QueryConst.COMMA)) {
      return value;
    }


    final boolean present = Arrays.stream(QueryRuleEnum.values())
      .map(QueryRuleEnum::getCondition)
      .map(this::ruleValueBlank)
      .anyMatch(value::startsWith);
    if (present) {
      return value.substring(3);
    }

    String needValue = value;
    if (needValue.startsWith(QueryConst.NOT_MARKER)) {
      needValue = needValue.substring(1);
    }
    if (needValue.startsWith(QueryConst.LIKE)) {
      needValue = needValue.substring(1);
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
    }
    // not in
    if (value.startsWith(QueryConst.NOT_MARKER + QueryConst.lEFT_BRACKET)) {
      return value.substring(2, value.length() - 1).split(QueryConst.COMMA);
    }
    return value.split(QueryConst.COMMA);
  }

  private String ruleValueBlank(String str) {
    return str + " ";
  }

}
