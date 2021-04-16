package io.github.yanfeiwuji.web.query.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author yanfeiwuji
   2021/4/14 4:47 下午
 */
public interface WrapperInstall {
  void install(QueryWrapper wrapper, WebQueryParam dbQueryParam);
}
