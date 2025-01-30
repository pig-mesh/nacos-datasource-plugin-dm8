# Nacos 达梦数据库插件

[![Maven Central](https://img.shields.io/maven-central/v/com.pig4cloud.plugin/nacos-datasource-plugin-dm8.svg?style=flat-square)](https://maven.badges.herokuapp.com/maven-central/com.pig4cloud.plugin/nacos-datasource-plugin-dm8)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## 项目介绍

本插件为 Nacos（2.2.0 版本及以上）提供达梦数据库（DM8）的数据源支持。通过 SPI 机制实现，您只需在 `application.properties` 配置文件中修改 `spring.datasource.platform` 属性即可启用达梦数据库。

![Nacos 插件架构](https://minio.pigx.top/oss/202212/1671179590.jpg)

> Nacos 官方默认支持 MySQL 和 Derby 数据库，本插件扩展了对达梦数据库的支持。

## 版本兼容性

| Nacos 版本    | 插件版本 |
|--------------|---------|
| 2.2.0 - 2.3.0 | 0.0.2   |
| 2.3.1 - 2.3.2 | 0.0.3   |
| 2.4.0 - 2.4.3 | 0.0.4   |
| 2.5.0+        | 0.0.5   |

## 快速开始

### 1. 添加依赖

在项目的 `pom.xml` 中添加以下依赖（注意：依赖已上传至 Maven 中央仓库，请勿使用阿里云代理）：

```xml
<!-- 达梦数据库插件 -->
<dependency>
    <groupId>com.pig4cloud.plugin</groupId>
    <artifactId>nacos-datasource-plugin-dm8</artifactId>
    <version>${plugin.version}</version>
</dependency>

<!-- 达梦数据库驱动 -->
<dependency>
    <groupId>com.dameng</groupId>
    <artifactId>DmJdbcDriver18</artifactId>
    <version>8.1.1.193</version>
</dependency>
```

### 2. 数据库迁移

从 MySQL 迁移到达梦数据库有以下两种推荐方案：

#### 方案一：使用 SQLark 迁移工具
SQLark 提供全流程的异构数据库迁移服务，通过自动化语法解析，提前识别可能存在的改造工作，生成最佳迁移策略。

![SQLark 迁移工具](https://minio.pigx.vip/oss/202501/1738162693.png)

详细迁移指南请访问 [SQLark 文档](https://www.sqlark.com/docs/zh/v1/data-migration/overview.html)

#### 方案二：使用达梦 DTS 迁移工具
达梦 DTS 工具支持将 Nacos 的 MySQL 数据库无缝迁移至达梦数据库。

![达梦 DTS 迁移](https://minio.pigx.top/oss/202406/1718112771.png)

### 3. 配置数据源

在 Nacos 配置文件中添加以下配置：

```yaml
db:
  num: 1
  url:
    0: jdbc:dm://127.0.0.1:5236?schema=PIGXX_CONFIG
  user: SYSDBA
  password: SYSDBA
  pool:
    config:
      driver-class-name: dm.jdbc.driver.DmDriver
```

### 4. 启用达梦数据库

在应用配置中设置数据库平台为达梦：

```yaml
spring:
  datasource:
    platform: dameng
```

## 参与贡献

我们欢迎所有形式的贡献，如果您有任何改进建议或功能扩展，请提交 Pull Request。

## 开源协议

本项目采用 Apache License 2.0 开源协议 - 详情请参见 [LICENSE](LICENSE) 文件。
