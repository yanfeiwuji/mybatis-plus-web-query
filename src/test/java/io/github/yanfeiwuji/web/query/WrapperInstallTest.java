package io.github.yanfeiwuji.web.query;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.yanfeiwuji.web.query.wrapper.WebQueryParam;
import io.github.yanfeiwuji.web.query.wrapper.impl.*;
import org.junit.Test;

/**
 * @author yanfeiwuji
   2021/4/15 6:45 下午
 */
public class WrapperInstallTest {

  DefaultWrapperInstall install = new DefaultWrapperInstall(
    new DefaultQueryToWrapperHasNullOp(),
    new DefaultQueryToWrapperMultOp(),
    new DefaultQueryToWrapperSingleOp(),
    new DefaultQueryValueToPrueValue()
  );

  @Test
  public void testWrapperInstall() {

    QueryWrapper wrapper = new QueryWrapper();


    WebQueryParam webQueryParam = new WebQueryParam("key", "tvt", "123");
    install.install(wrapper, webQueryParam);
    wrapper.getTargetSql();


    final String s = JSONUtil.toJsonStr(wrapper.getParamNameValuePairs());
    System.out.println(s);
    System.out.println(wrapper.getTargetSql());
  }
}
