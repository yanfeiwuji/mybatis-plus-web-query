package io.github.yanfeiwuji.web.query.wrapper.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yanfeiwuji.web.query.QueryConst;
import io.github.yanfeiwuji.web.query.wrapper.*;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.Map;

/**
 * @author yanfeiwuji
 * 2021/4/14 5:20 下午
 */
@AllArgsConstructor
public class DefaultWrapperInstall implements WrapperInstall {

  private final QueryToWrapperHasNullOp queryToWrapperHasNullOp;

  private final QueryToWrapperMultOp queryToWrapperMultOp;

  private final QueryToWrapperSingleOp queryToWrapperOp;

  private final QueryValueToPrueValue queryValueToPrueValue;

  private final ObjectMapper mapper;

  @Override
  public void install(Class c, QueryWrapper wrapper, WebQueryParam webQueryParam) {

    final String column = webQueryParam.getColumn();
    // 检查or
    checkOr(wrapper, webQueryParam);

    final WrapperHasNullOp wrapperHasNullOp = queryToWrapperHasNullOp.queryToOp(wrapper, webQueryParam);
    if (wrapperHasNullOp != null) {
      wrapperHasNullOp.exec(true, column);
      return;
    }

    // 获取值
    final Object[] prueValues = queryValueToPrueValue.convert(webQueryParam.getValue());

    final WrapperMultOp wrapperMultOp = queryToWrapperMultOp.queryToOp(wrapper, webQueryParam);

    // 执行查询
    if (wrapperMultOp != null) {
      wrapperMultOp.exec(true, column, prueValues);
      return;
    }

    final WrapperOp wrapperOp = queryToWrapperOp.queryToOp(wrapper, webQueryParam);

    // not null
    if (wrapperOp != null && prueValues != null && prueValues.length == 1) {

      String key = webQueryParam.getKey();
      if (key.endsWith(QueryConst.BEGIN)) {
        String needKey = key.substring(0, key.length() - QueryConst.BEGIN.length());
        wrapperOp.exec(true, column, handlerDate(c, needKey, prueValues[0].toString()));
        return;
      }
      if (key.endsWith(QueryConst.END)) {
        String needKey = key.substring(0, key.length() - QueryConst.END.length());
        wrapperOp.exec(true, column, handlerDate(c, needKey, prueValues[0].toString()));
        return;
      }
      wrapperOp.exec(true, column, prueValues[0]);
      return;
    }
    // handler one and more
  }

  private WrapperRule toWrapperRule(String value) {
    return WrapperRule.markToWrapperRule(valueToMark(value));
  }

  private String valueToMark(String value) {
    return value.split(" ", 2)[0];
  }


  private void checkOr(QueryWrapper wrapper, WebQueryParam webQueryParam) {
    final String value = webQueryParam.getValue();
    if (value.startsWith(QueryConst.OR_MARK_WITH_BLANK)) {
      wrapper.or();
    }
  }


  public Object handlerDate(Class<?> clazz, String key, String value) {
    final Map<String, String> build = MapUtil.builder(key, value).build();
    final String jsonStr;
    Object o = null;
    try {
      jsonStr = mapper.writeValueAsString(build);
      o = mapper.readValue(jsonStr, clazz);
    } catch (IOException e) {
      return value;
    }

    if (o != null) {
      return BeanUtil.getFieldValue(o, key);

    } else {
      return value;
    }
  }


}
