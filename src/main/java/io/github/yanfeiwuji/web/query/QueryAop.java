package io.github.yanfeiwuji.web.query;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 添加order 在cache 前生效
 *
 * @author yanfeiwuji
   2021/3/27 5:43 下午
 */
@CacheEvict
@Aspect
@Order(0)
public class QueryAop {
  private static final String QUERY_PREFIX = "BaseQuery<";
  private static final String QUERY_SUFFIX = ">";


  private static final Pattern PATTERN = Pattern.compile(QUERY_PREFIX + ".*" + QUERY_SUFFIX);

  private static final String DEFAULT_QUERY_CLASS = "com.yfwj.web.query.BaseQuery";

  //  和baseQuery
  @Before(value = "execution(public * *(io.github.yanfeiwuji.web.query.BaseQuery,..))")
  public void queryBefore(JoinPoint joinPoint) {
    // not null
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    // 给request 设置值
    MethodSignature sign = (MethodSignature) joinPoint.getSignature();
    final Method method = sign.getMethod();
    final String generic = method.toGenericString();
    final String queryClass = getQueryClass(generic, joinPoint);
    // 对继承的需要处理
    requestAttributes.setAttribute(QueryConst.QUERY_KEY, queryClass, RequestAttributes.SCOPE_REQUEST);
  }

  private String getQueryClass(String generic, JoinPoint joinPoint) {
    final Matcher matcher = PATTERN.matcher(generic);
    String queryClassName = null;
    if (matcher.find()) {
      queryClassName = matcher.group().replaceFirst(QUERY_PREFIX, "").replace(QUERY_SUFFIX, "");
    }
    // 不包含. 为泛形
    System.out.println(queryClassName);
    if (queryClassName != null) {
      if (queryClassName.contains(DEFAULT_QUERY_CLASS) && queryClassName.contains(QueryConst.DOT)) {
        queryClassName = getTQueryClass(joinPoint);
      }
    }
    if (queryClassName != null) {
      return queryClassName;
    }
    throw new QueryClassNotFoundException();
  }

  // 获取泛形的T class
  private String getTQueryClass(JoinPoint joinPoint) {
    final Class<?> targetClass = AopUtils.getTargetClass(joinPoint.getTarget());

    final Type[] actualTypeArguments = ((ParameterizedType) targetClass.getGenericSuperclass()).getActualTypeArguments();
    if (actualTypeArguments.length >= 1) {
      return actualTypeArguments[0].getTypeName();
    }
    return DEFAULT_QUERY_CLASS;
  }

}
