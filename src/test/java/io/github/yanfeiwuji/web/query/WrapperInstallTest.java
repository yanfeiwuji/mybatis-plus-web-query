package io.github.yanfeiwuji.web.query;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yanfeiwuji.web.query.wrapper.WebQueryParam;
import io.github.yanfeiwuji.web.query.wrapper.impl.*;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLOutput;

/**
 * @author yanfeiwuji
 * 2021/4/15 6:45 下午
 */
@SpringBootTest
public class WrapperInstallTest {


  @Test
  public void testWrapperInstall() {

    QueryWrapper wrapper = new QueryWrapper();


//    WebQueryParam webQueryParam = new WebQueryParam("key", "tvt", "!*123*");
//    install.install(TestEntity.class, wrapper, webQueryParam);
//    final String targetSql = wrapper.getTargetSql();
//    System.out.println(targetSql);

    System.out.println(NumberUtil.isNumber("123"));
    System.out.println(NumberUtil.parseLong("123"));
    final String s = JSONUtil.toJsonStr(wrapper.getParamNameValuePairs());
    System.out.println(s);
  }
}
