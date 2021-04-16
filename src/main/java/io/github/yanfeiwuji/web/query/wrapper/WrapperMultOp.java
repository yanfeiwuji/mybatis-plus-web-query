package io.github.yanfeiwuji.web.query.wrapper;

/**
 * @author yanfeiwuji
   2021/4/16 2:29 下午
 */
@FunctionalInterface
public interface WrapperMultOp {
  void accept(boolean condition, Object column, Object... obj);

  default void exec(boolean condition, Object column, Object... objs) {
    // not null
    accept(condition, column, objs);
  }
}
