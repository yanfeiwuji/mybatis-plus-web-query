package com.yfwj.web.query;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import lombok.Data;

import java.util.List;

/**
 * @author yanfeiwuji
 * @date 2021/4/13 11:10 上午
 */
@Data
public class PageVo {

  private Long current;
  private Long size;
  private List<OrderItem> orders;
}
