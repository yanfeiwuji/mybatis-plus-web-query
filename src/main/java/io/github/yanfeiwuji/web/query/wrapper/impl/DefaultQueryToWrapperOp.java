package io.github.yanfeiwuji.web.query.wrapper.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.yanfeiwuji.web.query.QueryConst;
import io.github.yanfeiwuji.web.query.wrapper.QuertToWrapperOp;
import io.github.yanfeiwuji.web.query.wrapper.WebQueryParam;
import io.github.yanfeiwuji.web.query.wrapper.WrapperOp;
import io.github.yanfeiwuji.web.query.wrapper.WrapperRule;


/**
 * @author yanfeiwuji
 * @date 2021/4/14 5:35 下午
 */
public class DefaultQueryToWrapperOp implements QuertToWrapperOp {


  @Override
  public WrapperOp queryToOp(QueryWrapper wrapper, WebQueryParam webQueryParam) {
    final String key = webQueryParam.getKey();
    final String value = webQueryParam.getValue();

    // 处理rule
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
      }
    }

    // 处理BEGIN and END
    if (key.endsWith(QueryConst.BEGIN)) {
      return wrapper::ge;
    }
    if (key.endsWith(QueryConst.END)) {
      return wrapper::le;
    }

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

    if (value.startsWith(QueryConst.NOT_MARKER) && value.contains(QueryConst.LIKE)) {
      return wrapper::notLike;
    }
    // 处理like
    return null;
  }

  private WrapperRule toWrapperRule(String value) {
    return WrapperRule.markToWrapperRule(valueToMark(value));
  }

  private String valueToMark(String value) {
    return value.split(" ", 2)[0];
  }

}
