package io.github.yanfeiwuji.web.query.wrapper.impl;

import cn.hutool.core.util.StrUtil;
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
    currentValue = handlerNot(currentValue);
    currentValue = handlerBrackets(currentValue);
    currentValue = handlerLike(currentValue);
    currentValue = handlerRule(currentValue);

    return new String[]{currentValue};
  }


  // remove !
  @Override
  public String handlerNot(String value) {
    if (value.startsWith(QueryConst.NOT_MARKER)) {
      return value.substring(1);
    }
    return value;
  }

  /**
   * 处理括号
   *
   * @param value
   * @return
   */
  @Override
  public String handlerBrackets(String value) {
    if (value.startsWith(QueryConst.lEFT_BRACKET) && value.endsWith(QueryConst.RIGHT_BRACKET)) {
      return value.substring(1, value.length() - 1);
    }
    return value;
  }


  @Override
  public String handlerLike(String value) {
    if (value.startsWith(QueryConst.LIKE) && value.endsWith(QueryConst.LIKE)) {
      return value.substring(1, value.length() - 1);
    }
    if (value.startsWith(QueryConst.LIKE)) {
      return value.substring(1);
    }
    if (value.endsWith(QueryConst.LIKE)) {
      return value.substring(0, value.length() - 1);
    }
    return value;
  }

  @Override
  public String handlerRule(String value) {
    final String rule =
      Arrays.stream(QueryRuleEnum.values())
        .map(QueryRuleEnum::getCondition)
        .map(this::ruleValueBlank)
        .filter(value::startsWith).findFirst()
        .orElse("");
    return value.replace(rule, "");
  }

  @Override
  public String handlerOr(String value) {
    String orMark = ruleValueBlank(QueryConst.OR_MARK);
    if (value.startsWith(orMark)) {
      return value.replace(orMark, "");
    }
    return value;
  }

  private String ruleValueBlank(String str) {
    return str + " ";
  }
}
