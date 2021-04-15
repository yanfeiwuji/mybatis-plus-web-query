package io.github.yanfeiwuji.web.query.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author yanfeiwuji
 * @date 2021/4/14 5:30 下午
 */
@FunctionalInterface
public interface QuertToWrapperOp {
  WrapperOp queryToOp(QueryWrapper wrapper, WebQueryParam value);
}
