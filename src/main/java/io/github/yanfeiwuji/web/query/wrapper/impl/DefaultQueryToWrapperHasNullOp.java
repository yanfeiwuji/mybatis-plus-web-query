package io.github.yanfeiwuji.web.query.wrapper.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.yanfeiwuji.web.query.QueryConst;
import io.github.yanfeiwuji.web.query.wrapper.QueryToWrapperHasNullOp;
import io.github.yanfeiwuji.web.query.wrapper.WebQueryParam;
import io.github.yanfeiwuji.web.query.wrapper.WrapperHasNullOp;
import lombok.AllArgsConstructor;

/**
 * @author yanfeiwuji
   2021/4/15 8:12 上午
 */
@AllArgsConstructor
public class DefaultQueryToWrapperHasNullOp implements QueryToWrapperHasNullOp {

  @Override
  public WrapperHasNullOp queryToOp(QueryWrapper wrapper, WebQueryParam webQueryParam) {
    final String value = webQueryParam.getValue();
    if (QueryConst.NOT_NULL_STR.equals(value)) {
      return wrapper::isNotNull;
    } else if (QueryConst.NULL_STR.equals(value)) {
      return wrapper::isNull;
    }
    return null;
  }
}
