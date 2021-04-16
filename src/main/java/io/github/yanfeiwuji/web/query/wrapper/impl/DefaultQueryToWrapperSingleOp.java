package io.github.yanfeiwuji.web.query.wrapper.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.yanfeiwuji.web.query.QueryConst;
import io.github.yanfeiwuji.web.query.wrapper.QueryToWrapperSingleOp;
import io.github.yanfeiwuji.web.query.wrapper.WebQueryParam;
import io.github.yanfeiwuji.web.query.wrapper.WrapperOp;
import io.github.yanfeiwuji.web.query.wrapper.WrapperRule;


/**
 * @author yanfeiwuji
   2021/4/14 5:35 下午
 */
public class DefaultQueryToWrapperSingleOp implements QueryToWrapperSingleOp {

  @Override
  public WrapperOp queryToOp(QueryWrapper wrapper, WebQueryParam param) {
    // option 链式调用
    WrapperOp wrapperOp = handlerRule(wrapper, param);
    if (wrapperOp != null) {
      return wrapperOp;
    }

    wrapperOp = handlerBegin(wrapper, param);
    if (wrapperOp != null) {
      return wrapperOp;
    }

    wrapperOp = handlerEnd(wrapper, param);
    if (wrapperOp != null) {
      return wrapperOp;
    }

    wrapperOp = handlerLike(wrapper, param);
    if (wrapperOp != null) {
      return wrapperOp;
    }


    wrapperOp = handlerNot(wrapper, param);
    if (wrapperOp != null) {
      return wrapperOp;
    }

    return wrapper::eq;
  }

  @Override
  public WrapperOp handlerRule(QueryWrapper wrapper, WebQueryParam param) {
    // 处理rule
    String value = param.getValue();
    final WrapperRule wrapperRule = toWrapperRule(value);
    if (wrapperRule != null) {
      switch (wrapperRule) {
        case LT:
          return wrapper::lt;
        case LE:
          return wrapper::le;
        case GT:
          return wrapper::gt;
        case GE:
          return wrapper::ge;
        case NE:
          return wrapper::ne;
        default:
          return null;
      }
    }
    return null;
  }

  @Override
  public WrapperOp handlerLike(QueryWrapper wrapper, WebQueryParam param) {
    String value = param.getValue();
    // 处理like
    if (value.startsWith(QueryConst.LIKE) && !value.endsWith(QueryConst.LIKE)) {
      return wrapper::likeLeft;
    }

    if (!value.startsWith(QueryConst.LIKE) && value.endsWith(QueryConst.LIKE)) {
      return wrapper::likeRight;
    }

    if (value.startsWith(QueryConst.LIKE) && value.endsWith(QueryConst.LIKE)) {
      return wrapper::like;
    }

    if (value.startsWith(QueryConst.NOT_MARKER + QueryConst.LIKE) &&
      value.endsWith(QueryConst.LIKE)) {
      return wrapper::notLike;
    }
    return null;
  }

  @Override
  public WrapperOp handlerBegin(QueryWrapper wrapper, WebQueryParam param) {
    // 处理BEGIN and END
    String key = param.getKey();
    if (key.endsWith(QueryConst.BEGIN)) {
      return wrapper::ge;
    }
    return null;
  }

  @Override
  public WrapperOp handlerEnd(QueryWrapper wrapper, WebQueryParam param) {
    String key = param.getKey();
    if (key.endsWith(QueryConst.END)) {
      return wrapper::le;
    }
    return null;
  }

//  @Override
//  public WrapperOp handlerIn(QueryWrapper wrapper, WebQueryParam param) {
//    final String value = param.getValue();
//    //有逗号不是一左括号开头
//    if (value.contains(QueryConst.COMMA) && !value.startsWith(QueryConst.NOT_MARKER + QueryConst.lEFT_BRACKET)) {
//      return wrapper::in;
//    }
//
//    return null;
//  }
//
//  @Override
//  public WrapperOp handlerNotIn(QueryWrapper wrapper, WebQueryParam param) {
//    final String value = param.getValue();
//    if (value.contains(QueryConst.COMMA) && value.startsWith(QueryConst.NOT_MARKER + QueryConst.lEFT_BRACKET)) {
//      return wrapper::notIn;
//    }
//    return null;
//  }

  @Override
  public WrapperOp handlerNot(QueryWrapper wrapper, WebQueryParam param) {
    String value = param.getValue();
    if (value.startsWith(QueryConst.NOT_MARKER)) {
      return wrapper::ne;
    }
    return null;
  }


  private WrapperRule toWrapperRule(String value) {
    return WrapperRule.markToWrapperRule(valueToMark(value));
  }

  private String valueToMark(String value) {
    return value.split(" ", 2)[0];
  }


}
