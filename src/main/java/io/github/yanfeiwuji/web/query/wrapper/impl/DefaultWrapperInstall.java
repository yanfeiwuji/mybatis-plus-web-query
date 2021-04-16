package io.github.yanfeiwuji.web.query.wrapper.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.yanfeiwuji.web.query.QueryConst;
import io.github.yanfeiwuji.web.query.wrapper.*;
import lombok.AllArgsConstructor;

/**
 * @author yanfeiwuji
   2021/4/14 5:20 下午
 */
@AllArgsConstructor
public class DefaultWrapperInstall implements WrapperInstall {


  private final QueryToWrapperHasNullOp queryToWrapperHasNullOp;

  private final QueryToWrapperMultOp queryToWrapperMultOp;

  private final QueryToWrapperSingleOp queryToWrapperOp;

  private final QueryValueToPrueValue queryValueToPrueValue;


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

    // 获取值
    final Object[] prueValues = queryValueToPrueValue.convert(webQueryParam.getValue());

    final WrapperMultOp wrapperMultOp = queryToWrapperMultOp.queryToOp(wrapper, webQueryParam);

    // 执行查询
    if (wrapperMultOp != null) {
      wrapperMultOp.exec(true, column, prueValues);
      return;
    }

    final WrapperOp wrapperOp = queryToWrapperOp.queryToOp(wrapper, webQueryParam);

    // not null
    if (wrapperOp != null && prueValues != null && prueValues.length == 1) {
      wrapperOp.exec(true, column, prueValues[0]);
      return;
    }
    // handler one and more
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
