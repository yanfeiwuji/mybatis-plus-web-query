package io.github.yanfeiwuji.web.query.wrapper;

/**
 * @author yanfeiwuji
 * @date 2021/4/15 8:47 上午
 */
public interface QueryValueToPrueValue {

  // 单值,多值,
  Object[] convert(String value);


  String handlerNot(String value);

  String handlerBrackets(String value);

  String handlerLike(String value);

  String handlerRule(String value);

  String handlerOr(String value);
}