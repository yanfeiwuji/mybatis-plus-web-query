package io.github.yanfeiwuji.web.query.wrapper.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.yanfeiwuji.web.query.QueryConst;
import io.github.yanfeiwuji.web.query.wrapper.QueryToWrapperMultOp;
import io.github.yanfeiwuji.web.query.wrapper.WebQueryParam;
import io.github.yanfeiwuji.web.query.wrapper.WrapperMultOp;

/**
 * @author yanfeiwuji
   2021/4/16 2:31 下午
 */
public class DefaultQueryToWrapperMultOp implements QueryToWrapperMultOp {
  @Override
  public WrapperMultOp queryToOp(QueryWrapper wrapper, WebQueryParam param) {
    WrapperMultOp wrapperMultOp = handlerIn(wrapper, param);
    if (wrapperMultOp != null) {
      return wrapperMultOp;
    }

    wrapperMultOp = handlerNotIn(wrapper, param);
    if (wrapperMultOp != null) {
      return wrapperMultOp;
    }
    return null;
  }

  @Override
  public WrapperMultOp handlerIn(QueryWrapper wrapper, WebQueryParam param) {
    final String value = param.getValue();
    //有逗号不是一左括号开头
    if (value.contains(QueryConst.COMMA) && !value.startsWith(QueryConst.NOT_MARKER + QueryConst.lEFT_BRACKET)) {
      return wrapper::in;
    }

    return null;
  }

  @Override
  public WrapperMultOp handlerNotIn(QueryWrapper wrapper, WebQueryParam param) {

    final String value = param.getValue();
    if (value.contains(QueryConst.COMMA) && value.startsWith(QueryConst.NOT_MARKER + QueryConst.lEFT_BRACKET)) {
      return wrapper::notIn;
    }
    return null;
  }
}
