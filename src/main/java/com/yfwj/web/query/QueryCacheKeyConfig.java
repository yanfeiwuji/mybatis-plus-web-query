package com.yfwj.web.query;

import cn.hutool.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanfeiwuji
 * @date 2021/4/13 11:49 上午
 */
@Configuration
public class QueryCacheKeyConfig {

  @Bean
  public CustomQueryCacheKey customQueryCacheKey() {
    return JSONObject::toString;
  }

}
