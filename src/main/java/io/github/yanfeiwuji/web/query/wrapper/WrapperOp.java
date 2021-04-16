package io.github.yanfeiwuji.web.query.wrapper;


/**
 * wrapper 操作 不包括 isNull isNotNull
 *
 * @author yanfeiwuji
   2021/4/14 4:59 下午
 */
@FunctionalInterface
public interface WrapperOp {

  void accept(boolean condition, Object column, Object obj);

  default void exec(boolean condition, Object column, Object obj) {
    // not null
    accept(condition, column, obj);
  }

}
