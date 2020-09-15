# MyBatis

**环境：**

- JDK1.8
- MySQL 5.7
- Maven 3.6.1
- IDEA

**回顾：**

- JDBC
- MySQL
- Java基础
- Maven
- Junit

**如何获得：**

maven仓库：

```xml
<dependency>
  <groupId>org.mybatis</groupId>
  <artifactId>mybatis</artifactId>
  <version>3.5.2</version>
</dependency>
```

官网：https://mybatis.org/mybatis-3/zh/index.html

Github：https://github.com/mybatis/mybatis-3

----

### 1. 简介

#### 1.1 什么是MyBatis

![image-20200915151221635](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200915151221635.png)

- MyBatis 是一款优秀的**持久层框架**
- 它支持自定义 SQL、存储过程以及高级映射。
- MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作。
- MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。
- MyBatis 本是[apache](https://baike.so.com/doc/5333438-5568873.html)的一个开源项目[iBatis](https://baike.so.com/doc/1381914-1460868.html), 2010年这个项目由apache software foundation 迁移到了google code，并且改名为MyBatis 。
- 2013年11月迁移到Github。

#### 1.2 持久化

数据持久化

- 持久化就是将程序的数据在持久状态和瞬时状态转化的过程；

为什么需要持久化？

- 有一些对象，不能丢失；
- 内存太贵了；

#### 1.3 持久层

- 完成持久化工作的代码块；
- 层是界限十分明显的；

#### 1.4 为什么需要MyBatis？

- 帮助程序员将数据存入到数据库中；
- 方便；
- 传统的JDBC代码太复杂；
- 简化、框架、自动化；
- 更容易上手；

#### 1.5 优点

- **简单易学:**本身就很小且简单。没有任何第三方依赖，最简单安装只要两个jar文件+配置几个sql映射文件易于学习，易于使用，通过文档和源代码，可以比较完全的掌握它的设计思路和实现。
- **灵活:**mybatis不会对应用程序或者数据库的现有设计强加任何影响。 sql写在xml里，便于统一管理和优化。通过sql语句可以满足操作数据库的所有需求。
- 解除sql与程序代码的耦合:通过提供DAO层，将业务逻辑和数据访问逻辑分离，使系统的设计更清晰，更易维护，更易单元测试。**sql和代码的分离，提高了可维护性。**
- **提供映射标签，支持对象与数据库的orm字段关系映射**
- **提供对象关系映射标签，支持对象关系组建维护**
- **提供xml标签**，支持编写动态sql。

### 2. 第一个MyBatis程序















































