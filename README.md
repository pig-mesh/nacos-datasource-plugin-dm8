Nacos 从 2.2.0 版本开始,可通过 SPI 机制注入多数据源实现插件,并在引入对应数据源实现后,便可在 Nacos 启动时通过读取
application.properties 配置文件中 spring.datasource.platform 配置项选择加载对应多数据源插件.

![Nacos 插件化实现
](https://minio.pigx.top/oss/202212/1671179590.jpg)

> Nacos 官方默认实现 MySQL、Derby ，其他类型数据库接入需要参考下文自己扩展。

![](https://minio.pigx.top/oss/202212/1671180565.png)

## 自定义dm8 插件

### 1.增加dm8数据库插件

> 依赖已上传 maven 中央仓库，请勿使用阿里云代理

| NACOS 版本      | 插件版本  |
|---------------|-------|
| 2.2.0 - 2.3.0 | 0.2.0 |
| 2.3.1 - 2.3.2 | 0.3.0 |

```xml
<!--达梦数据库插件-->
<dependency>
    <groupId>com.pig4cloud.plugin</groupId>
    <artifactId>nacos-datasource-plugin-dm8</artifactId>
    <version>${VERSION}</version>
</dependency>

<dependency>
<groupId>com.dameng</groupId>
<artifactId>DmJdbcDriver18</artifactId>
<version>8.1.1.193</version>
</dependency>
```

## 2. 使用达梦DTS迁移工具

达梦DTS能够支持将Nacos原版的MYSQL数据库迁移至支持达梦数据库的脚本。

<img src='https://minio.pigx.top/oss/202406/1718112771.png' alt='1718112771'/>

## 3.配置 nacos 数据源链接信息

```
db:
  num: 1
  url:
    0: jdbc:dm://172.27.0.5:5236?schema=nacos
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
