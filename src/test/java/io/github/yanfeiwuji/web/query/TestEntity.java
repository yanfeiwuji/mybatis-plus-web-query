package io.github.yanfeiwuji.web.query;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

/**
 * @author yanfeiwuji
 */

public class TestEntity {


  @DateTimeFormat(pattern = "yyyy-mm-dd")
  String date;
}
