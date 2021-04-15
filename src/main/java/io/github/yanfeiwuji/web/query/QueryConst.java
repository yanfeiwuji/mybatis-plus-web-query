package io.github.yanfeiwuji.web.query;


import cn.hutool.core.util.StrUtil;

/**
 * @author yanfeiwuji
 * @date 2021/3/27 6:26 下午
 */
public interface QueryConst {
  String QUERY_KEY = "yfwj-query-key";
  String NOT_MARKER = "!";

  String NULL_MARKER = "null";
  // 只对时间
  String BEGIN = "_begin";
  // 只对时间
  String END = "_end";

  String LIKE = "*";

  String COMMA = ",";

  String COLON = ":";

  String DOT = ".";

  String lEFT_BRACKET = "(";

  String RIGHT_BRACKET = ")";

  String OR_MARK = "or";
}
