package io.github.yanfeiwuji.web.query.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.NonNull;

/**
 * @author yanfeiwuji
 * @date 2021/4/14 4:44 下午
 */
@Data
@AllArgsConstructor
public class WebQueryParam {
  @NonNull
  private String key;
  @NonNull
  private String value;

}
