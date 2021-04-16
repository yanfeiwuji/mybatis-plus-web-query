package io.github.yanfeiwuji.web.query.wrapper;

/**
 * @author yanfeiwuji
   2021/4/15 8:47 上午
 */
public interface QueryValueToPrueValue {

  // 单值,多值,
  Object[] convert(String value);


  String handlerOr(String value);

  String handlerStart(String value);

  String handlerEnd(String value);

  //String handlerBrackets(String value);


  Object[] handlerMult(String value);
}
