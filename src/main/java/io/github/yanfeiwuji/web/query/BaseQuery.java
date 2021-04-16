package io.github.yanfeiwuji.web.query;

import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.yanfeiwuji.web.query.wrapper.WebQueryParam;
import io.github.yanfeiwuji.web.query.wrapper.WrapperInstall;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yanfeiwuji
   2021/4/13 11:09 上午
 */
@Data
public class BaseQuery<T> {

  private static final WrapperInstall wrapperInstall = SpringUtil.getBean(WrapperInstall.class);

  @Getter
  @Setter
  private PageVo page;

  private static final String PAGE_PREFIX = "page.";

  protected final QueryWrapper<T> wrapper = new QueryWrapper<>();

  private List<Map.Entry<String, String[]>> queryParams = null;


  private JSONObject queryParamsJSONObject = null;


  public LambdaQueryWrapper<T> lambdaQueryWrapper() {
    return wrapper.lambda();
  }


  public Page<T> page() {
    Page<T> needPage = new Page<>();

    if (page == null) {
      return needPage;
    }

    if (page.getCurrent() != null) {
      needPage.setCurrent(page.getCurrent());
    }

    if (page.getSize() != null) {
      needPage.setSize(page.getSize());
    }

    if (page.getOrders() != null) {
      needPage.setOrders(page.getOrders());
    }

    return needPage;
  }

  public QueryWrapper<T> query() {
    Class clazz = findQueryClass();

    final List<Map.Entry<String, String[]>> entries = loadQueryParams();
    // install wrapper
    entries.forEach(entry -> {
      final String key = entry.getKey();
      Arrays.stream(entry.getValue()).forEach(value -> {
        System.out.println(value);
        wrapperInstall.install(
          wrapper,
          new WebQueryParam(
            key,
            MybatisPlusUtil.entityPropertyToColumn(clazz, key),
            value
          ));
      });
    });
    // TODO it
    return wrapper;
  }


  @SneakyThrows
  public List<Map.Entry<String, String[]>> loadQueryParams() {

    if (queryParams != null) {
      return queryParams;
    }

    final Map<String, String[]> parameterMap =
      Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
        .map(ServletRequestAttributes::getRequest)
        .map(HttpServletRequest::getParameterMap)
        .orElse(MapUtil.newHashMap());

    Class queryClass = findQueryClass();
    final List<Map.Entry<String, String[]>> entries = filterFiled(queryClass, parameterMap);
    this.queryParams = entries;
    return entries;
  }

  public Class findQueryClass() {
    final String queryClassName = this.findRequestAttribute(QueryConst.QUERY_KEY)
      .orElseThrow(QueryClassNotFoundException::new);
    // 获取属性名
    try {
      return Class.forName(queryClassName);
    } catch (ClassNotFoundException e) {
      throw new QueryClassNotFoundException();
    }
  }

  public Optional<String> findRequestAttribute(String attrName) {
    // 获取request
    //  获取类名
    return Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
      .map(s -> s.getAttribute(attrName, RequestAttributes.SCOPE_REQUEST))
      .map(Objects::toString);
  }


  @SneakyThrows
  private List<Map.Entry<String, String[]>> filterFiled(Class queryClass, Map<String, String[]> parameterMap) {

    List<Field> fields = new ArrayList<>();
    fields.addAll(Arrays.asList(queryClass.getFields()));
    fields.addAll(Arrays.asList(queryClass.getDeclaredFields()));

    // 过滤 属性
    final Set<String> fieldNames = fields.stream().filter(field -> {
      TableField tableField = field.getAnnotation(TableField.class);
      return tableField == null || tableField.exist();
    }).map(Field::getName).collect(Collectors.toSet());

    // 返回值
    return parameterMap.entrySet().stream().filter((entry) -> {

      // 把 begin and end 去掉
      final String key = entry.getKey()
        .replace(QueryConst.BEGIN, "")
        .replace(QueryConst.END, "");

      return !key.startsWith(PAGE_PREFIX) && fieldNames.contains(key);
    }).collect(Collectors.toList());
  }

  private JSONObject queryParamsJSONObject() {

    if (queryParamsJSONObject != null) {
      return queryParamsJSONObject;
    }

    final List<Map.Entry<String, String[]>> entries = this.loadQueryParams();
    JSONObject jsonObject = new JSONObject();
    entries.forEach(entry -> {
        final String s = entry.getValue()[0];
        jsonObject.append(entry.getKey(), s);
      }
    );

    queryParamsJSONObject = jsonObject;
    return jsonObject;
  }


  public String cacheKey() {
    return cacheKeyWith();
  }


  // 将需要的对象放里面一起做序列化key
  public String cacheKeyWith(Object... o) {
    final List<Object> collect = Arrays.stream(o).collect(Collectors.toList());
    // @TODO 这边需要根据环境选择不同的 模式，开发用json部署用 hash

    return queryToJson().append("others", collect).toString();
  }

  private JSONObject queryToJson() {
    final JSONObject jsonObject = new JSONObject();
    jsonObject.append("page", this.page);
    jsonObject.append("query", this.queryParamsJSONObject());
    return jsonObject;
  }
}

