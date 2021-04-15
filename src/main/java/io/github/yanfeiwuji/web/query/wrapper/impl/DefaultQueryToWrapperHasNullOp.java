package io.github.yanfeiwuji.web.query.wrapper.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.yanfeiwuji.web.query.QueryConst;
import io.github.yanfeiwuji.web.query.wrapper.QueryToWrapperHasNullOp;
import io.github.yanfeiwuji.web.query.wrapper.WebQueryParam;
import io.github.yanfeiwuji.web.query.wrapper.WrapperHasNullOp;
import lombok.AllArgsConstructor;

/**
 * @author yanfeiwuji
 * @date 2021/4/15 8:12 上午
 */
@AllArgsConstructor
public class DefaultQueryToWrapperHasNullOp implements QueryToWrapperHasNullOp {

  //private final QueryValueToPrueValue valueToPrueValue;

  @Override
  public WrapperHasNullOp queryToOp(QueryWrapper wrapper, WebQueryParam webQueryParam) {
    final String value = webQueryParam.getValue();

    //valueToPrueValue.convert(value);
    if (value.startsWith(QueryConst.NOT_MARKER)) {

    }
    return null;
  }
}
