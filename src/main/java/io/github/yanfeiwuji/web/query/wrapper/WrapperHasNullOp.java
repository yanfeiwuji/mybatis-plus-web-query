package io.github.yanfeiwuji.web.query.wrapper;

/**
 * @author yanfeiwuji
   2021/4/14 5:16 下午
 */
@FunctionalInterface
public interface WrapperHasNullOp {
  void accept(boolean condition, Object column);

  default void exec(boolean condition, Object column) {
    accept(condition, column);
  }
}
