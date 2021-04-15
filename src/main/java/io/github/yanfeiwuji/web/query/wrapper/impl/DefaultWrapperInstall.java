package io.github.yanfeiwuji.web.query.wrapper.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.yanfeiwuji.web.query.wrapper.WebQueryParam;
import io.github.yanfeiwuji.web.query.wrapper.WrapperInstall;
import io.github.yanfeiwuji.web.query.wrapper.WrapperRule;

/**
 * @author yanfeiwuji
 * @date 2021/4/14 5:20 下午
 */
public class DefaultWrapperInstall implements WrapperInstall {


  @Override
  public void install(QueryWrapper wrapper, WebQueryParam dbQueryParam) {

    final String value = dbQueryParam.getValue();
    final WrapperRule wrapperRule = toWrapperRule(value);
  }

  private WrapperRule toWrapperRule(String value) {
    return WrapperRule.markToWrapperRule(valueToMark(value));
  }

  private String valueToMark(String value) {
    return value.split(" ", 2)[0];
  }

}
