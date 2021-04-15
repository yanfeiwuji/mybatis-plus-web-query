package io.github.yanfeiwuji.web.query.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author yanfeiwuji
 * @date 2021/4/15 8:11 上午
 */
public interface QueryToWrapperHasNullOp {

  WrapperHasNullOp queryToOp(QueryWrapper wrapper, WebQueryParam webQueryParam);

}
