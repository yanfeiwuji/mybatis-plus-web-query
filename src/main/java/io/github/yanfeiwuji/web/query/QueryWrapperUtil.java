package io.github.yanfeiwuji.web.query;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

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

    queryWrapperAddNot(queryWrapper, dbColumn, pureValue);
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
    } else if (QueryConst.NULL_MARKER.equalsIgnoreCase(value)) {
      queryWrapper.isNull(dbColumn);
    } else if (value.startsWith(QueryConst.NOT_MARKER)) {
      final String pureValue = value.substring(1);
      queryWrapperAddNot(queryWrapper, dbColumn, pureValue);
    } else {
      queryWrapper.eq(dbColumn, value);
    }
  }


  private void queryWrapperAddNot(QueryWrapper queryWrapper, String dbColumn, String pureValue) {

    // 多值
    if (pureValue.startsWith(QueryConst.lEFT_BRACKET) &&
      pureValue.endsWith(QueryConst.RIGHT_BRACKET) &&
      pureValue.contains(QueryConst.COMMA)
    ) {
      String pureValues = pureValue.substring(1, pureValue.length() - 1);
      queryWrapper.notIn(dbColumn, pureValues.split(QueryConst.COMMA));
      return;
    }

    if (QueryConst.NULL_MARKER.equalsIgnoreCase(pureValue)) {
      queryWrapper.isNotNull(dbColumn);
    } else if (pureValue.startsWith(QueryConst.lEFT_BRACKET) &&
      pureValue.endsWith(QueryConst.RIGHT_BRACKET)
    ) {
      String pure = pureValue.substring(1, pureValue.length() - 1);
      queryWrapper.ne(dbColumn, pure);
    } else {
      queryWrapper.ne(dbColumn, pureValue);

    }
  }

  private String valueToNeed(String value) {

    return value.split(" ", 2)[1];
  }

  private static final Pattern p0 = Pattern.compile("\\(.*?\\)");
  private static final Pattern p = Pattern.compile(",.*?,");

  public static void main(String[] args) {

  }


}
