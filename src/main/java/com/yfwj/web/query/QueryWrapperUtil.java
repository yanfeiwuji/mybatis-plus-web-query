package com.yfwj.web.query;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.experimental.UtilityClass;

/**
 * @author yanfeiwuji
 * @date 2021/3/27 7:53 下午
 */
@UtilityClass
public class QueryWrapperUtil {

  public void installWrapper(Class queryClass, QueryWrapper queryWrapper, String key, String value) {

    String needKey = key.replaceFirst(QueryConst.BEGIN, "")
      .replaceFirst(QueryConst.END, "");
    String dbColumn = MybatisPlusUtil.entityPropertyToColumn(queryClass, needKey);

    //  final Set<String> rule = Arrays.stream(QueryRuleEnum.values()).map(QueryRuleEnum::getValue).collect(Collectors.toSet());

    final QueryRuleEnum queryRuleEnum = QueryRuleEnum.valueToQueryRuleEnum(value);

    if (queryRuleEnum != null) {
      installWrapperByQueryRuleEnum(queryWrapper, key, value, queryRuleEnum, dbColumn);
      return;
    }

    boolean isNot = value.startsWith(QueryConst.NOT_MARKER);
    if (isNot) {
      installWrapperByNot(queryWrapper, key, value, dbColumn);
      return;
    }

    boolean isMult = value.contains(QueryConst.COMMA);
    if (isMult) {
      installWrapperByMult(queryWrapper, key, value, dbColumn);
      return;
    }

    boolean isBeginOrEnd = key.endsWith(QueryConst.BEGIN) || key.endsWith(QueryConst.END);
    if (isBeginOrEnd) {
      installWrapperByBeginAndEnd(queryWrapper, key, value, dbColumn);
      return;
    }
    installWrapperSingle(queryWrapper, key, value, dbColumn);


  }

  private void installWrapperByQueryRuleEnum(
    QueryWrapper queryWrapper,
    String key,
    String value,

    QueryRuleEnum queryRuleEnum, String dbColumn) {
    String need = valueToNeed(value);
    switch (queryRuleEnum) {
      case GT:
        queryWrapper.gt(dbColumn, need);
        break;
      case GE:
        queryWrapper.ge(dbColumn, need);
        break;
      case LT:
        queryWrapper.lt(dbColumn, need);
        break;
      case LE:
        queryWrapper.le(dbColumn, need);
        break;
    }

    return;
  }


  private void installWrapperByNot(QueryWrapper queryWrapper, String key, String value, String dbColumn) {

    String pureValue = value.replaceFirst(QueryConst.NOT_MARKER, "");

    boolean isMult = pureValue.contains(QueryConst.COMMA);
    if (isMult) {
      queryWrapper.notIn(dbColumn, pureValue.split(","));
    } else {
      queryWrapper.ne(dbColumn, value);
    }
  }

  private void installWrapperByMult(QueryWrapper queryWrapper, String key, String value, String dbColumn) {
    // ,多值 or   连接,
    if (value.startsWith(QueryConst.COMMA) && value.endsWith(QueryConst.COMMA)) {
      String[] vals = value.substring(1, value.length() - 1).split(",");
      for (int i = 0; i < vals.length; i++) {
        installWrapperSingle(queryWrapper, key, vals[i], dbColumn);
        queryWrapper.or();
      }
      return;
    }

    queryWrapper.in(dbColumn, value.split(QueryConst.COMMA));
  }

  private void installWrapperByBeginAndEnd(QueryWrapper queryWrapper, String key, String value, String dbColumn) {
    String needKey = key.replaceFirst(QueryConst.BEGIN, "")
      .replaceFirst(QueryConst.END, "");
    if (key.endsWith(QueryConst.BEGIN)) {

      queryWrapper.ge(NumberUtil.isNumber(value), dbColumn, DateUtil.date(NumberUtil.parseLong(value)).toJdkDate());
    } else if (key.endsWith(QueryConst.END)) {

      queryWrapper.le(NumberUtil.isNumber(value), dbColumn, DateUtil.date(NumberUtil.parseLong(value)).toJdkDate());
    }

  }

  private void installWrapperSingle(QueryWrapper queryWrapper, String key, String value, String dbColumn) {

    if (value.startsWith(QueryConst.LIKE) && value.endsWith(QueryConst.LIKE)) {
      final String needValue = value.substring(1, value.length() - 1);
      queryWrapper.like(dbColumn, needValue);
    } else if (value.startsWith(QueryConst.LIKE)) {
      final String needValue = value.substring(1);
      queryWrapper.likeLeft(dbColumn, needValue);
    } else if (value.endsWith(QueryConst.LIKE)) {
      final String needValue = value.substring(0, value.length() - 1);
      queryWrapper.likeRight(dbColumn, needValue);
    } else {
      queryWrapper.eq(dbColumn, value);
    }
  }


  private String valueToNeed(String value) {
    return value.split(" ", 2)[1];
  }


}
