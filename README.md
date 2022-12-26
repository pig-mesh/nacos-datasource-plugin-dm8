## 1.增加dm8数据库插件
```xml
<!--达梦数据库插件-->
<dependency>
    <groupId>com.pig4cloud.plugin</groupId>
    <artifactId>nacos-datasource-plugin-dm8</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>

<dependency>
    <groupId>com.dameng</groupId>
    <artifactId>DmJdbcDriver18</artifactId>
    <version>8.1.1.193</version>
</dependency>
```

## 2.导入 nacos dm8 数据库脚本
再达梦数据库管理工具上创建名为 `nacos` 的表空间，导入一下数据库脚本  
[数据库脚本](./sql/nacos.sql)

## 3.配置 nacos 数据源链接信息
```aidl
db:
  num: 1
  url:
    0: jdbc:dm://172.27.0.5:5236/nacos
  user: nacos
  password: nacos@123
  pool:
    config:
      driver-class-name: dm.jdbc.driver.DmDriver
```


## 4.执行数据库平台
```aidl
spring:
  datasource:
    platform: dameng 
```