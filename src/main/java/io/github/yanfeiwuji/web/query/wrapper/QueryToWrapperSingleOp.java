package io.github.yanfeiwuji.web.query.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author yanfeiwuji
   2021/4/14 5:30 下午
 */
public interface QueryToWrapperSingleOp {
  WrapperOp queryToOp(QueryWrapper wrapper, WebQueryParam param);

  WrapperOp handlerRule(QueryWrapper wrapper, WebQueryParam param);

  WrapperOp handlerLike(QueryWrapper wrapper, WebQueryParam param);

  WrapperOp handlerBegin(QueryWrapper wrapper, WebQueryParam param);

  WrapperOp handlerEnd(QueryWrapper wrapper, WebQueryParam param);

  WrapperOp handlerNot(QueryWrapper wrapper, WebQueryParam param);

}
