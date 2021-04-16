package io.github.yanfeiwuji.web.query;


import lombok.Getter;

import java.util.Arrays;

/**
 * @author yanfeiwuji
   2021/3/27 6:26 下午
 */
@Getter
public enum QueryRuleEnum {

  GT(">", "gt", "大于"),
  GE(">=", "ge", "大于等于"),
  LT("<", "lt", "小于"),
  LE("<=", "le", "小于等于"),
  // !为不等于
  NE("!=", "ne", "不等于");

  // 默认eq
  //EQ("=", "eq", "等于"),
  // ,隔离用,,,来表示
  //IN("IN", "in", "包含"),
  // *张 ｜ 张* ｜ *张* ｜ *张*三*
  //LIKE("LIKE", "like", "全模糊"),
  //LEFT_LIKE("LEFT_LIKE", "left_like", "左模糊"),
  // RIGHT_LIKE("RIGHT_LIKE", "right_like", "右模糊");


  private String value;

  private String condition;

  private String msg;

  QueryRuleEnum(String value, String condition, String msg) {
    this.value = value;
    this.condition = condition;
    this.msg = msg;
  }

  public static QueryRuleEnum valueToQueryRuleEnum(String value) {
    final boolean hasSpace = value.contains(" ");
    // 有空格就取第一个
    if (hasSpace) {
      String[] conditions = value.split(" ", 2);
      // 得到条件
      String condition = conditions[0];
      return conditionToQueryRuleEnum(condition);
    }
    return null;
  }

  public static QueryRuleEnum conditionToQueryRuleEnum(String condition) {
    return Arrays.stream(QueryRuleEnum.values())
      .filter(queryRuleEnum -> queryRuleEnum.getCondition()
        .equals(condition)).findFirst().orElse(null);
  }

}
