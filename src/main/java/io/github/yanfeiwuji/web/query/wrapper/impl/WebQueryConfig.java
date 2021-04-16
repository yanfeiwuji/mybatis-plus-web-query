package io.github.yanfeiwuji.web.query.wrapper.impl;

import io.github.yanfeiwuji.web.query.wrapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanfeiwuji
   2021/4/16 1:48 下午
 */
@Configuration
public class WebQueryConfig {

  @Bean
  @ConditionalOnMissingBean
  public QueryToWrapperSingleOp defaultQueryToWrapperOp() {
    return new DefaultQueryToWrapperSingleOp();
  }

  @Bean
  @ConditionalOnMissingBean
  public QueryToWrapperHasNullOp defaultQueryToWrapperHasNullOp() {
    return new DefaultQueryToWrapperHasNullOp();
  }

  @Bean
  @ConditionalOnMissingBean
  public QueryValueToPrueValue defaultQueryValueToPrueValue() {
    return new DefaultQueryValueToPrueValue();
  }

  @Bean
  @ConditionalOnMissingBean
  public QueryToWrapperMultOp queryToWrapperMultOp() {
    return new DefaultQueryToWrapperMultOp();
  }

  @Bean
  @ConditionalOnMissingBean
  @Autowired
  public WrapperInstall defaultWrapperInstall(

    QueryToWrapperHasNullOp wrapperHasNullOp,

    QueryToWrapperMultOp wrapperMultOp,
    QueryToWrapperSingleOp wrapperSingleOp,
    QueryValueToPrueValue valueToPrueValue
  ) {
    return new DefaultWrapperInstall(wrapperHasNullOp, wrapperMultOp, wrapperSingleOp, valueToPrueValue);
  }

}
