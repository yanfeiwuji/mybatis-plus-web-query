package io.github.yanfeiwuji.web.query;

import cn.hutool.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanfeiwuji
   2021/4/13 11:49 上午
 */
@Configuration
public class QueryCacheKeyConfig {


  @Bean
  public CustomQueryCacheKey customQueryCacheKey() {
    return JSONObject::toString;
  }

}
