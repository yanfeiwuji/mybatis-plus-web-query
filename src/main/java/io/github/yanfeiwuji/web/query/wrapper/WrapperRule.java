package io.github.yanfeiwuji.web.query.wrapper;

import lombok.AllArgsConstructor;

import java.util.Arrays;

/**
 * @author yanfeiwuji
 * @date 2021/4/14 5:38 下午
 */
@AllArgsConstructor
public enum WrapperRule {
  GT("gt"),
  GE("ge"),
  LT("lt"),
  LE("le"),
  // !为不等于
  NE("ne");
  String MARK;

  public static WrapperRule markToWrapperRule(String mark) {
    return Arrays.stream(WrapperRule.values())
      .filter(s -> s.MARK.equals(mark))
      .findFirst().orElse(null);
  }
}
