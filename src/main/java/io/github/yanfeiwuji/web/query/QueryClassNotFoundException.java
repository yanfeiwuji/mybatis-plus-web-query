package io.github.yanfeiwuji.web.query;

/**
 * @author yanfeiwuji
   2021/4/13 11:31 上午
 */
public class QueryClassNotFoundException extends RuntimeException {
  public QueryClassNotFoundException() {
    super("BaseQuery 未添加实体泛形");
  }
}
