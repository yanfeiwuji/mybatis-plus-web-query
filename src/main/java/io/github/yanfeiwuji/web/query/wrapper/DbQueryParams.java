package io.github.yanfeiwuji.web.query.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yanfeiwuji
 * @date 2021/4/14 4:20 下午
 */
@Data
@AllArgsConstructor
public class DbQueryParams {

  /**
   * 数据库列
   */
  private String column;
  private String[] values;

  public void installWrapper(QueryWrapper wrapper) {
    WrapperOp w = wrapper::notIn;

  }
}
