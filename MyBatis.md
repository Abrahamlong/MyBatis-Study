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

## 1. 简介

### 1.1 什么是MyBatis

![image-20200915151221635](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200915151221635.png)

- MyBatis 是一款优秀的**持久层框架**
- 它支持自定义 SQL、存储过程以及高级映射。
- MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作。
- MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。
- MyBatis 本是[apache](https://baike.so.com/doc/5333438-5568873.html)的一个开源项目[iBatis](https://baike.so.com/doc/1381914-1460868.html), 2010年这个项目由apache software foundation 迁移到了google code，并且改名为MyBatis 。
- 2013年11月迁移到Github。

### 1.2 持久化

数据持久化

- 持久化就是将程序的数据在持久状态和瞬时状态转化的过程；

为什么需要持久化？

- 有一些对象，不能丢失；
- 内存太贵了；

### 1.3 持久层

- 完成持久化工作的代码块；
- 层是界限十分明显的；

### 1.4 为什么需要MyBatis？

- 帮助程序员将数据存入到数据库中；
- 方便；
- 传统的JDBC代码太复杂；
- 简化、框架、自动化；
- 更容易上手；

### 1.5 优点

- **简单易学:**本身就很小且简单。没有任何第三方依赖，最简单安装只要两个jar文件+配置几个sql映射文件易于学习，易于使用，通过文档和源代码，可以比较完全的掌握它的设计思路和实现。
- **灵活:**mybatis不会对应用程序或者数据库的现有设计强加任何影响。 sql写在xml里，便于统一管理和优化。通过sql语句可以满足操作数据库的所有需求。
- 解除sql与程序代码的耦合:通过提供DAO层，将业务逻辑和数据访问逻辑分离，使系统的设计更清晰，更易维护，更易单元测试。**sql和代码的分离，提高了可维护性。**
- **提供映射标签，支持对象与数据库的orm字段关系映射**
- **提供对象关系映射标签，支持对象关系组建维护**
- **提供xml标签**，支持编写动态sql。

----

## 2. 第一个MyBatis程序

### 2.1 编写配置文件

- 编写MyBatis的配置类

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<!--configuration的核心配置文件·   -->
<configuration>

    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=utf-8"/>
                <property name="username" value="root"/>
                <property name="password" value="123456"/>
            </dataSource>
        </environment>
    </environments>
    <!--每一个Mapper.xml都需要在Mabatis的核心配置文件中注册-->
    <mappers>
        <com.longg.mapper resource="com.com.longg.dao.UserMapper.xml"/>
    </mappers>

</configuration>
```

- 编写MyBatis的工具类

```java
public class MybatisUtils {

    private static SqlSessionFactory sqlSessionFactory;

    /**
     * 使用MyBatis的第一步：获取sqlSessionFactory对象
     */
    static{
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 既然有了 SqlSessionFactory，顾名思义，我们可以从中获得 SqlSession 的实例。SqlSession 提供了在数据库
     * 执行 SQL 命令所需的所有方法。你可以通过 SqlSession 实例来直接执行已映射的 SQL 语句。
     */
    public static SqlSession getSqlSession(){
        SqlSession sqlSession = sqlSessionFactory.openSession();

        return sqlSession;
    }
}
```

### 2.2 编写代码

- 实体类

```java
public class User {
    private int id;
    private String name;
    private String pwd;

    public User() {
    }

    public User(int id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}

```

- Mapper接口：由原来的UserDao.java变成现在的UserMapper接口

```java
public interface UserMapper {
    public List<User> getUserList();
}
```

- 接口实现类(UserMapper.xml)：由原来的UserDaoImpl.java变成现在的Mapper配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE com.longg.mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-com.longg.mapper.dtd">
<!--namespace 绑定一个对应的Dao/Mapper接口-->
<com.longg.mapper namespace="com.com.longg.dao.UserMapper">

    <select id="getUserList" resultType="com.com.longg.pojo.User">
        select * from mybatis.user;
    </select>
</com.longg.mapper>
```

- 测试类

```java
public class UserDaoTest {
    @Test
    public void test(){

        /**
         * 第一步：获得SqlSession 的对象
         */
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        /**
         * 第二步：执行sql
         * 方式一：getMapper
         */
        UserMapper com.longg.mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = com.longg.mapper.getUserList();

        for (User user :
                userList) {
            System.out.println(user);
        }

        /**
         * 关闭SqlSession
         */
        sqlSession.close();
    }
}
```

- ### **==注意点：==**

  **==1.  报错：org.apache.ibatis.binding.BindingException: Type interface com.com.longg.dao.UserMapper is not known to the MapperRegistry.==**

  ==**MapperRegistry：Mapper注册中心：每一个Mapper.xml都需要在Mabatis的核心配置文件中注册**==

  **==解决方法：在MyBatis的核心配置文件中添加以下代码==**

  ```xml
      <mappers>
          <com.longg.mapper resource="com/com.longg/dao/UserMapper.xml"/>  
      </mappers>
  ```

  **==2.  Maven导出资源问题：由于maven的约定大于配置，所有可能会遇见我们写的配置文件无法被导出或者生效的问题.==**

  **==解决方法：在pom文件中添加以下代码==**

```xml
<!--在build中配置Resources，来防止我们的资源文件导出失败的问题-->
    <build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>
```

----

## 3. CRUD

### 3.1 select

选择、查询语句；

- id：就是对应命名空间namespace中的方法名；
- resultType：SQL语句执行的返回值；即实体类；
- parameterType：参数类型；



1. ##### 编写接口；

   ```java
   /**
     * 根据ID查询用户
     */
   User getUserById(int id);
   ```

2. ##### 编写对应的mapper中的sql语句；

   ```xml
   <select id="getUserById" parameterType="int" resultType="com.com.longg.pojo.User">
       select * from mybatis.user where id = #{id};
   </select>
   ```

3. ##### 测试；

   ```java
   @Test
   public void getUserById(){
       SqlSession sqlSession = MybatisUtils.getSqlSession();
   
       UserMapper com.longg.mapper = sqlSession.getMapper(UserMapper.class);
       User user = com.longg.mapper.getUserById(1);
       System.out.println(user);
   
       sqlSession.close();
   }
   ```

### 3.2 insert

1. ##### 编写接口；

   ```java
   /**
     * 插入一个用户
     */
   int addUser(User user);
   ```

2. ##### 编写对应的mapper中的sql语句；

   ```xml
   <insert id="addUser" parameterType="com.com.longg.pojo.User">
       insert into mybatis.user (id,name,pwd) values (#{id},#{name},#{pwd});
   </insert>
   ```

3. ##### 测试；

   ```java
   @Test
   public void addUser(){
       SqlSession sqlSession = MybatisUtils.getSqlSession();
   
       UserMapper com.longg.mapper = sqlSession.getMapper(UserMapper.class);
       int result = com.longg.mapper.addUser(new User(6, "long", "123456"));
       if(result > 0) {
           System.out.println("插入成功！");
       }
   
       // 提交事务
       sqlSession.commit();
   
       sqlSession.close();
   }
   ```

### 3.3 update

1. ##### 编写接口；

   ```java
   /**
     * 修改用户
     */
   int updateUser(User user);
   ```

2. ##### 编写对应的mapper中的sql语句；

   ```xml
   <update id="updateUser" parameterType="com.com.longg.pojo.User">
       update mybatis.user set name = #{name},pwd=#{pwd} where id=#{id};
   </update>
   ```

3. ##### 测试；

   ```java
   @Test
   public void updateUser(){
       SqlSession sqlSession = MybatisUtils.getSqlSession();
   
       UserMapper com.longg.mapper = sqlSession.getMapper(UserMapper.class);
       com.longg.mapper.updateUser(new User(4,"hhh","654321"));
   
       sqlSession.commit();
   
       sqlSession.close();
   }
   ```

### 3.4 delete

1. ##### 编写接口；

   ```java
   /**
     * 删除用户
     */
   int deleteUser(int id);
   ```

2. ##### 编写对应的mapper中的sql语句；

   ```xml
   <delete id="deleteUser" parameterType="int">
       delete from user where id = #{id};
   </delete>
   ```

3. ##### 测试；

   ```java
   @Test
   public void deleteUser(){
       SqlSession sqlSession = MybatisUtils.getSqlSession();
   
       UserMapper com.longg.mapper = sqlSession.getMapper(UserMapper.class);
       com.longg.mapper.deleteUser(6);
   
       sqlSession.commit();
   
       sqlSession.close();
   }
   ```

   #### **==注意点：增删改需要提交事务==**

### 3.5 Map

假设实体类或者数据库中的字段过多，我们需要考虑使用Map来传数据；

```java
/**
  * 用万能的map插入数据
  */
int addUserMap(Map<String ,Object> map);
```

```xml
<insert id="addUserMap" parameterType="map">
    insert into mybatis.user (id,name,pwd) values (#{userId},#{userName},#{password});
</insert>
```

```java
@Test
public void addUserMap(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();

    UserMapper com.longg.mapper = sqlSession.getMapper(UserMapper.class);

    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("userId",6);
    map.put("userName","long");
    map.put("password","123456");

    com.longg.mapper.addUserMap(map);

    sqlSession.commit();
    sqlSession.close();
}
```

### **==总结：==**

- 只有一个基本类型参数的情况下，可以直接在sql中取其参数，可以不用定义参数类型（也可以定义：【parameterType="int"】）；
- 对象传递参数的情况下，可以直接在sql中取其对象的属性；【parameterType="Object"（com.com.longg.pojo.User）】；
- Map传递参数的情况下，可以直接在sql中取其对应的key值；【parameterType="map"】

**如果是多个无规则的参数则可以用Map或者是注解；**

### 模糊查询

**模糊查询怎么写？**

​	**1. 在java代码执行的时候传递通配符 % %；【List<User> users = com.longg.mapper.getUserLike("%李%");】**

```java
/**
  * 模糊查询
  */
List<User> getUserLike(String str);
```

```xml
<select id="getUserLike" resultType="com.com.longg.pojo.User">
    select * from user where name like #{value};
</select>
```

```java
@Test
public void getUserLike() {
    SqlSession sqlSession = MybatisUtils.getSqlSession();

    UserMapper com.longg.mapper = sqlSession.getMapper(UserMapper.class);
    List<User> users = com.longg.mapper.getUserLike("%李%");
    for (User user : users) {
        System.out.println(user);
    }

    sqlSession.close();
}
```

​	**2. 在sql中拼接使用通配符% %；【select * from user where name like "%"#{value}"%";】**

```java
/**
  * 模糊查询
  */
List<User> getUserLike(String str);
```

```xml
<select id="getUserLike" resultType="com.com.longg.pojo.User">
    select * from user where name like "%"#{value}"%";
</select>
```

```java
@Test
public void getUserLike() {
    SqlSession sqlSession = MybatisUtils.getSqlSession();

    UserMapper com.longg.mapper = sqlSession.getMapper(UserMapper.class);
    List<User> users = com.longg.mapper.getUserLike("龙");
    for (User user : users) {
        System.out.println(user);
    }

    sqlSession.close();
}
```

----

## 4. 配置解析

### 4.1 核心配置文件

- mybatis-config.xml文件；
- MyBatis的配置文件包含并会深深的影响MyBatis的行为设置和属性信息；

![image-20200924083721067](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200924083721067.png)

​			注：标红的是需要掌握的；

### 4.2 环境配置（environment）

MyBatis可以配置多个不同的环境来适应多种环境的需要；通过【default=“  ”】来选择不同的环境；

**不过要记住：尽管可以配置多个环境，但每个 SqlSessionFactory 实例只能选择一种环境。**

学会使用配置来配置多套不同的运行环境：

```xml
<environments default="development">	<!--默认使用的环境 ID（如：default="test"）-->
    <environment id="development">
        <transactionManager type="JDBC"/>
        <dataSource type="POOLED">
            <property name="driver" value="com.mysql.jdbc.Driver"/>
            <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=utf-8"/>
            <property name="username" value="root"/>
            <property name="password" value="123456"/>
        </dataSource>
    </environment>
    
    <environment id="test"> <!--每个environment元素定义的环境ID-->
        <transactionManager type="JDBC"/>
        <dataSource type="POOLED">
            <property name="driver" value="com.mysql.jdbc.Driver"/>
            <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=utf-8"/>
            <property name="username" value="root"/>
            <property name="password" value="123456"/>
        </dataSource>
    </environment>
    
</environments>
```

Mybatis默认的事务管理器是：JDBC，连接池是：POOLED；

**注意点：**

- 默认使用的环境 ID（比如：default="development"）。
- 每个 environment 元素定义的环境 ID（比如：id="development"）。
- 事务管理器的配置（比如：type="JDBC"）。
- 数据源的配置（比如：type="POOLED"）。

### 4.3 属性（properties）

我们可以通过properties属性来实现引用配置文件；

这些属性可以在外部进行配置，并可以进行动态替换。你既可以在典型的 Java 属性文件中配置这些属性，也可以在 properties 元素的子元素中设置。【db.properties】

- 编写配置文件 db.properties

```properties
driver = com.mysql.jdbc.Driver
url = jdbc:mysql://localhost:3306/mybatis?useSSL=true&useUnicode=true&characterEncoding=utf-8"
username = root
password = 123456
```

- 在核心配置文件中映入

**==注意：==**在configuration中写这些配置的顺序必须是按照图中的顺序啦，否则将报错！！

![image-20200924100036219](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200924100036219.png)

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <!--引入外部配置文件-->
    <properties resource="db.properties"/>
    
    <environments default="test">
        </environment>
        <environment id="test">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>

    <mappers>
        <com.longg.mapper resource="com/com.longg/com.longg.mapper/UserMapper.xml"/>
    </mappers>
    
</configuration>
```

### 4.4 类型别名（typeAliases）

类型别名可为 Java 类型设置一个缩写名字。 它仅用于 XML 配置，意在降低冗余的全限定类名书写。

- 在核心配置文件中取别名

  - 给实体类取别名

    ```xml
    <!--可以给实体类取别名-->
    <typeAliases>
        <typeAlias type="com.com.longg.pojo.User" alias="User"/>
    </typeAliases>
    ```

    ```xml
    <select id="getUserList" resultType="User">
        select * from mybatis.user;
    </select>
    ```

  - 给对应的包取别名：可以指定一个包名，MyBatis 会在包名下面搜索需要的 Java Bean；每一个在包 `com.long.pojo` 中的 Java Bean，在没有注解的情况下，会使用 Bean 的首字母小写的非限定类名来作为它的别名。 比如`com.long.pojo.User`的别名为 `user`；若有注解，则别名为其注解值。

    ```xml
    <!--给对应的包取别名-->
    <typeAliases>
        <package name="com.com.longg.pojo"/>
    </typeAliases>
    ```

    ```xml
    <select id="getUserList" resultType="user">
        select * from mybatis.user;
    </select>
    ```

  区别：

  - 在实体类比较少的时候使用第一种方法；
  - 如果实体类比较多的情况下推荐使用第二种方法；
  - 第一种可以使用自定义别名，第二种只能使用注解自定义别名；【@Alias(" ")】

- 下面是一些为常见的 Java 类型内建的类型别名。它们都是不区分大小写的，注意，为了应对原始类型的命名重复，采取了特殊的命名风格。

|    别名    | 映射的类型 |
| :--------: | :--------: |
|   _byte    |    byte    |
|   _long    |    long    |
|   _short   |   short    |
|    _int    |    int     |
|  _integer  |    int     |
|  _double   |   double   |
|   _float   |   float    |
|  _boolean  |  boolean   |
|   string   |   String   |
|    byte    |    Byte    |
|    long    |    Long    |
|   short    |   Short    |
|    int     |  Integer   |
|  integer   |  Integer   |
|   double   |   Double   |
|   float    |   Float    |
|  boolean   |  Boolean   |
|    date    |    Date    |
|  decimal   | BigDecimal |
| bigdecimal | BigDecimal |
|   object   |   Object   |
|    map     |    Map     |
|  hashmap   |  HashMap   |
|    list    |    List    |
| arraylist  | ArrayList  |
| collection | Collection |
|  iterator  |  Iterator  |

### 4.5 设置（settings）

这是 MyBatis 中极为重要的调整设置，它们会改变 MyBatis 的运行时行为。

![image-20200924134922141](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200924134922141.png)

![image-20200924134848076](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200924134848076.png)

一个配置完整的 settings 元素的示例如下：

```
<settings>
  <setting name="cacheEnabled" value="true"/>
  <setting name="lazyLoadingEnabled" value="true"/>
  <setting name="multipleResultSetsEnabled" value="true"/>
  <setting name="useColumnLabel" value="true"/>
  <setting name="useGeneratedKeys" value="false"/>
  <setting name="autoMappingBehavior" value="PARTIAL"/>
  <setting name="autoMappingUnknownColumnBehavior" value="WARNING"/>
  <setting name="defaultExecutorType" value="SIMPLE"/>
  <setting name="defaultStatementTimeout" value="25"/>
  <setting name="defaultFetchSize" value="100"/>
  <setting name="safeRowBoundsEnabled" value="false"/>
  <setting name="mapUnderscoreToCamelCase" value="false"/>
  <setting name="localCacheScope" value="SESSION"/>
  <setting name="jdbcTypeForNull" value="OTHER"/>
  <setting name="lazyLoadTriggerMethods" value="equals,clone,hashCode,toString"/>
</settings>
```

### 4.6 映射器（mappers）

==**MapperRegistry：Mapper注册中心：每一个Mapper.xml文件都需要在Mabatis的核心配置文件中注册**==

- 注册方式一：使用资源文件绑定注册

```xml
<!-- 使用相对于类路径的资源引用 -->
<mappers>
  <com.longg.mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
  <com.longg.mapper resource="org/mybatis/builder/BlogMapper.xml"/>
  <com.longg.mapper resource="org/mybatis/builder/PostMapper.xml"/>
</mappers>

例：    
<mappers>
    <com.longg.mapper resource="com/com.longg/com.longg.mapper/UserMapper.xml"/>
</mappers>
```

- 注册方式二：使用class文件绑定注册

```xml
<!-- 使用映射器接口实现类的完全限定类名 -->
<mappers>
  <com.longg.mapper class="org.mybatis.builder.AuthorMapper"/>
  <com.longg.mapper class="org.mybatis.builder.BlogMapper"/>
  <com.longg.mapper class="org.mybatis.builder.PostMapper"/>
</mappers>

例：
<mappers>
    <com.longg.mapper class="com.com.longg.com.longg.mapper.UserMapper"/>
</mappers>
```

**注意点：**

		1. 接口和它的Mapper配置文件必须同名；
  		2. 接口和它的Mapper配置文件必须在同一个包下；

- 注册方式三：使用扫描包进行注入绑定

```xml
<!-- 将包内的映射器接口实现全部注册为映射器 -->
<mappers>
  <package name="org.mybatis.builder"/>
</mappers>

例：
<mappers>
    <package name="com.com.longg.com.longg.mapper"/>
</mappers>
```

**注意点：**

  		1. 接口和它的Mapper配置文件必须同名；
        		2. 接口和它的Mapper配置文件必须在同一个包下；

### 4.7 作用域（Scope）和生命周期

理解我们之前讨论过的不同作用域和生命周期类别是至关重要的，因为错误的使用会导致非常严重的并发问题。

- #### SqlSessionFactoryBuilder

  - 一旦创建了 SqlSessionFactory，就不再需要它了；
  - 局部变量；

- #### SqlSessionFactory

  - 简单的说就可以理解为：数据库连接池；

  - SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建另一个实例；
  - 因此 SqlSessionFactory 的最佳作用域是应用作用域；
  -  有很多方法可以做到，最简单的就是使用单例模式或者静态单例模式；

- #### SqlSession

  - 连接到连接池的一个请求；
  - SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域；
  - 用完之后即可关闭，否则容易导致资源占用；

----

## 5.解决属性名与字段名不一致的问题

数据库中的字段：

![image-20200924151315867](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200924151315867.png)

新建一个项目，拷贝之前的，测试实体类字段不一致的情况：

```java
public class User {
    private int id;
    private String name;
    private String password;
}
```

测试出现问题：

![image-20200924151457573](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200924151457573.png)

sql语句：

```xml
<select id="getUserById" parameterType="int" resultType="com.longg.pojo.User">
    select * from mybatis.user where id = #{id};
</select>

原始sql：select id,name,pwd from mybatis.user where id = #{id};
```

解决方法：（最不可取）

- 给pwd取别名：

```xml
<select id="getUserById" parameterType="int" resultType="com.longg.pojo.User">
    select id,name,pwd as password from mybatis.user where id = #{id};
</select>
```

- 用结果集映射，见5.1；

### 5.1 resultMap

结果集映射

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.longg.mapper.UserMapper">

    <!--结果集映射-->
    <resultMap id="UserMap" type="User">
        <!--colum为数据库中的字段，property为实体类中的字段-->
        <result column="id" property="id"/>
        <result column="name" property="name"/>
        <result column="pwd" property="password"/>
    </resultMap>
    
    <select id="getUserById" parameterType="int" resultMap="UserMap">
        select * from mybatis.user where id = #{id};
    </select>

</mapper>
```

- `resultMap` 元素是 MyBatis 中最重要最强大的元素；
- ResultMap 的设计思想是，对简单的语句做到零配置，对于复杂一点的语句，只需要描述语句之间的关系就行了；

- 如果数据库字段和实体类字段名相同的情况下可以不用映射相同的字段，只映射不同的字段，也可以成功；


```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.longg.mapper.UserMapper">

    <!--结果集映射-->
    <resultMap id="UserMap" type="User">
        <!--colum为数据库中的字段，property为实体类中的字段-->
        <result column="pwd" property="password"/>
    </resultMap>
    
    <select id="getUserById" parameterType="int" resultMap="UserMap">
        select * from mybatis.user where id = #{id};
    </select>

</mapper>
```

----

## 6. 日志

### 6.1 日志工厂

如果一个数据库操作，出现了异常，我们需要排错，日志就是最好的帮手；

原来：sout、debug；

现在：日志工厂；

![image-20200924160027401](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200924160027401.png)

- SLF4J 
-  LOG4J 【掌握】
-  LOG4J2 
-  JDK_LOGGING 
-  COMMONS_LOGGING 
-  STDOUT_LOGGING 【掌握】
-  NO_LOGGING

在MyBatis中具体使用哪一个日志实现，在设置中设定；

### 6.2 STDOUT_LOGGING 标准日志输出

在mybatis的核心配置文件中配置日志：

```xml
<settings>
    <setting name="logImpl" value="STDOUT_LOGGING"/>
</settings>
```

输出结果：

![image-20200924161136461](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200924161136461.png)



### 6.3 Log4J

什么是Log4J？

- Log4j是Apache的一个开源项目，通过使用Log4j，我们可以控制日志信息输送的目的地是控制台、文件、GUI组件；
- 我们可以控制每一条日志的输出格式；
- 通过定义每一条日志信息的级别，我们能够更加细致地控制日志的生成过程；
- 可以通过一个配置文件来灵活地进行配置，而不需要修改应用的代码；



1. 先导入log4j 的包

```xml
<dependency>
    <groupId>apache-log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.15</version>
</dependency>
```

2. log4j.properties文件配置

```properties
### 设置输出sql的级别，其中logger后面的内容全部为jar包中所包含的包名 ###
log4j.logger.org.apache=DEBUG
log4j.logger.java.sql.Connection=DEBUG
log4j.logger.java.sql.Statement=DEBUG
log4j.logger.java.sql.PreparedStatement=DEBUG
log4j.logger.java.sql.ResultSet=DEBUG

### 配置输出到控制台 ###
log4j.appender.console = org.apache.log4j.ConsoleAppender
log4j.appender.console.Target = System.out
log4j.appender.console.Threshold = DEBUG
log4j.appender.console.layout = org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern =  %d{ABSOLUTE} %5p %c{ 1 }:%L - %m%n

### 配置输出到文件 ###
log4j.appender.file = org.apache.log4j.FileAppender
log4j.appender.file.File = ./logs/long.log
log4j.appender.file.MaxFileSize = 100KB 
log4j.appender.file.Append = true
log4j.appender.file.Threshold = DEBUG
log4j.appender.file.layout = org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern = %-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n
```

3. 在mybatis的核心配置文件中配置 `LOG4J` 日志：

```xml
<settings>
    <setting name="logImpl" value="LOG4J"/>
</settings>
```

**简单使用：**

1. 在要使用Log4j 的类中，导入对应的包：import org.apache.log4j.Logger;
2. 日志对象，参数为当前类的class；

```java
Logger logger = Logger.getLogger(UserMapperTest.class);
```

3. 日志级别

```java
logger.info("info:进入了testlog4j");
logger.debug("debug:进入了testlog4j");
logger.error("error:进入了testlog4j");
```

4. 测试输出

![image-20200924192515378](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200924192515378.png)























