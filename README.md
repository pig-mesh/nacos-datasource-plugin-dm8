Nacos 从 2.2.0 版本开始,可通过 SPI 机制注入多数据源实现插件,并在引入对应数据源实现后,便可在 Nacos 启动时通过读取 application.properties 配置文件中 spring.datasource.platform 配置项选择加载对应多数据源插件.

![Nacos 插件化实现
](https://minio.pigx.top/oss/202212/1671179590.jpg)

> Nacos 官方默认实现 MySQL、Derby ，其他类型数据库接入需要参考下文自己扩展。

![](https://minio.pigx.top/oss/202212/1671180565.png)

## 自定义dm8 插件

### 1.增加dm8数据库插件

> 依赖已上传 maven 中央仓库，请勿使用阿里云代理


```xml
<!--达梦数据库插件-->
<dependency>
    <groupId>com.pig4cloud.plugin</groupId>
    <artifactId>nacos-datasource-plugin-dm8</artifactId>
    <version>0.0.1</version>
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
```
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
```
spring:
  datasource:
    platform: dameng 
```
