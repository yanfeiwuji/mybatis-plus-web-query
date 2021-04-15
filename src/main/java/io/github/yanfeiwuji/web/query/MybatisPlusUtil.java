package io.github.yanfeiwuji.web.query;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanfeiwuji
 * @date 2021/3/27 10:13 下午
 */
@UtilityClass
public class MybatisPlusUtil {

  /**
   * 使用缓存来提高效率
   */
  private Map<String, String> cache = new HashMap<>();

  public String entityPropertyToColumn(Class c, String property) {
    final String column = cache.get(getKey(c, property));
    if (column != null) {
      return column;
    }
    //String putColumn = null;


    final TableInfo tableInfo = TableInfoHelper.getTableInfo(c);

    // property 前面过滤过了
    String putColumn = tableInfo
      .getFieldList()
      .stream()
      .filter(tableFieldInfo ->
        tableFieldInfo.getProperty().equals(property)
      ).map(TableFieldInfo::getColumn)
      .findFirst()
      .orElse(tableInfo.getKeyColumn());


    cache.put(getKey(c, property), putColumn);
    return putColumn;
  }

  private String getKey(Class c, String property) {
    return c.getName() + QueryConst.COLON + property;
  }
}
