package com.yfwj.web.query;

/**
 * @author yanfeiwuji
 * @date 2021/4/13 11:31 上午
 */
public class QueryClassNotFoundException extends RuntimeException {
  public QueryClassNotFoundException() {
    super("BaseQuery 未添加 泛形");
  }
}
