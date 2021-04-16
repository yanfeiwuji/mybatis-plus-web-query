# mybatis-plus查询插件

为mybatis添加方便的单表查询

## 使用

### 1要有mybatis-plus开发环境 [mybatis-plus](https://mp.baomidou.com/)

### 2.maven中添加依赖

```xml

<dependency>
  <groupId>io.github.yanfeiwuji</groupId>
  <artifactId>mybatis-plus-web-query</artifactId>
  <version>1.0.0-RELEASE</version>
</dependency>
```

### 3.controller

```java
@GetMapping
public Page<SysUser> baseQuery(BaseQuery<SysUser> query){
  return sysUserMapper.selectPage(query.page(),query.query());
  }
```

### 4.效果

|  query params   | sql  |sql params|desc|
|  ----  | ----  |---- |----|
| username:A  | username = ? | A(String) ||
| username:!A  | username <> ?| A(String)||
| username:!null | username IS NOT NULL| ||
| username:null | username IS NULL| ||
|username:1*|username LIKE ?| 1%(String)||
|username:*1|username LIKE ?|%1(String)||
|username:\*1\*|username LIKE ?|%1%(String)||
|age:ge 1|age >= ?|1(String)|中间有一个空格|
|age:gt 1|age > ?|1(String)|中间有一个空格|
|age:le 1|age <= ?|1(String)|中间有一个空格|
|age:lt 1|age < ?|1(String)|中间有一个空格|
|age:ne 1|age <> ?|1(String)|中间有一个空格|
|username:11,A|username IN (?,?)|11(String), A(String)||
|username:!(11,A)|username NOT IN (?, ?)|11(String), A(String)||
|username:!\*1\*|username NOT LIKE ?|%1%(String)|
|birthDay_begin:1970-01-01 birthDay_end:1970-01-02|birth_day >= ? AND birth_day <= ?|1970-01-01 08:00:00.0(Timestamp), 1970-01-02 08:00:00.0(Timestamp)|和你的jackson的配置是相同的|
|username:*11 username:*A|username LIKE ? AND username LIKE ?|%11(String), %A(String)||
|username:*11 username:or *A|username LIKE ? OR username = ?|%11(String), A(String)||

### 5.联系我

+ 微信:yanfeiwuji
+ qq:1327800522
+ email:1327800522@qq.com
