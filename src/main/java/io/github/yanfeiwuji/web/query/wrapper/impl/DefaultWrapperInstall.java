package io.github.yanfeiwuji.web.query.wrapper.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.yanfeiwuji.web.query.QueryConst;
import io.github.yanfeiwuji.web.query.wrapper.*;
import lombok.AllArgsConstructor;

/**
 * @author yanfeiwuji
 * @date 2021/4/14 5:20 下午
 */
@AllArgsConstructor
public class DefaultWrapperInstall implements WrapperInstall {


  private final DefaultQueryToWrapperHasNullOp queryToWrapperHasNullOp;

  private final DefaultQueryToWrapperOp queryToWrapperOp;

  private final DefaultQueryValueToPrueValue queryValueToPrueValue;

  @Override
  public void install(QueryWrapper wrapper, WebQueryParam webQueryParam) {

    final String column = webQueryParam.getColumn();
    // 检查or
    checkOr(wrapper, webQueryParam);

    final WrapperHasNullOp wrapperHasNullOp = queryToWrapperHasNullOp.queryToOp(wrapper, webQueryParam);
    if (wrapperHasNullOp != null) {
      wrapperHasNullOp.exec(true, column);
      return;
    }


    final WrapperOp wrapperOp = queryToWrapperOp.queryToOp(wrapper, webQueryParam);
    final Object[] prueValues = queryValueToPrueValue.convert(webQueryParam.getValue());

    // not null
    if (wrapperOp == null) {
      return;
    }
    wrapperOp.exec(true, column, prueValues);
  }

  private WrapperRule toWrapperRule(String value) {
    return WrapperRule.markToWrapperRule(valueToMark(value));
  }

  private String valueToMark(String value) {
    return value.split(" ", 2)[0];
  }


  private void checkOr(QueryWrapper wrapper, WebQueryParam webQueryParam) {
    final String value = webQueryParam.getValue();
    if (value.startsWith(QueryConst.OR_MARK_WITH_BLANK)) {
      wrapper.or();
    }
  }
}
