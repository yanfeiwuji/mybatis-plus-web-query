package io.github.yanfeiwuji.web.query;

import cn.hutool.json.JSONObject;

/**
 * @author yanfeiwuji
 * @date 2021/4/13 11:46 上午
 */
@FunctionalInterface
public interface CustomQueryCacheKey {
  String jsonToCacheKey(JSONObject jsonObject);


}
