package io.github.yanfeiwuji.web.query.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author yanfeiwuji
   2021/4/16 2:31 下午
 */
public interface QueryToWrapperMultOp {
  WrapperMultOp queryToOp(QueryWrapper wrapper, WebQueryParam param);

  WrapperMultOp handlerIn(QueryWrapper wrapper, WebQueryParam param);

  WrapperMultOp handlerNotIn(QueryWrapper wrapper, WebQueryParam param);
}
