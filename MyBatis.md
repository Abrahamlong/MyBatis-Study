

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

==【工程1：mybatis-01】==

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
        <mapper resource="com.longg.dao.UserMapper.xml"/>
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
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--namespace 绑定一个对应的Dao/Mapper接口-->
<mapper namespace="com.longg.dao.UserMapper">

    <select id="getUserList" resultType="com.longg.pojo.User">
        select * from mybatis.user;
    </select>
</mapper>
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

==【工程1：mybatis-01】==

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
   <select id="getUserById" parameterType="int" resultType="com.longg.pojo.User">
       select * from mybatis.user where id = #{id};
   </select>
   ```

3. ##### 测试；

   ```java
   @Test
   public void getUserById(){
       SqlSession sqlSession = MybatisUtils.getSqlSession();
   
       UserMapper mapper = sqlSession.getMapper(UserMapper.class);
       User user = mapper.getUserById(1);
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
   <insert id="addUser" parameterType="com.longg.pojo.User">
       insert into mybatis.user (id,name,pwd) values (#{id},#{name},#{pwd});
   </insert>
   ```

3. ##### 测试；

   ```java
   @Test
   public void addUser(){
       SqlSession sqlSession = MybatisUtils.getSqlSession();
   
       UserMapper mapper = sqlSession.getMapper(UserMapper.class);
       int result = mapper.addUser(new User(6, "long", "123456"));
       if(result > 0) {
           System.out.println("插入成功！");
       }
   
       // 提交事务（手动）
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
   <update id="updateUser" parameterType="com.longg.pojo.User">
       update mybatis.user set name = #{name},pwd=#{pwd} where id=#{id};
   </update>
   ```

3. ##### 测试；

   ```java
   @Test
   public void updateUser(){
       SqlSession sqlSession = MybatisUtils.getSqlSession();
   
       UserMapper mapper = sqlSession.getMapper(UserMapper.class);
       mapper.updateUser(new User(4,"hhh","654321"));
   
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
   
       UserMapper mapper = sqlSession.getMapper(UserMapper.class);
       mapper.deleteUser(6);
   
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

    UserMapper mapper = sqlSession.getMapper(UserMapper.class);

    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("userId",6);
    map.put("userName","long");
    map.put("password","123456");

    mapper.addUserMap(map);

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
<select id="getUserLike" resultType="com.longg.pojo.User">
    select * from user where name like #{value};
</select>
```

```java
@Test
public void getUserLike() {
    SqlSession sqlSession = MybatisUtils.getSqlSession();

    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    List<User> users = mapper.getUserLike("%李%");
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
<select id="getUserLike" resultType="com.longg.pojo.User">
    select * from user where name like "%"#{value}"%";
</select>
```

```java
@Test
public void getUserLike() {
    SqlSession sqlSession = MybatisUtils.getSqlSession();

    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    List<User> users = mapper.getUserLike("龙");
    for (User user : users) {
        System.out.println(user);
    }

    sqlSession.close();
}
```

----

## 4. 配置解析

==【工程2：mybatis-02】==

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
        <mapper resource="com/longg/mapper/UserMapper.xml"/>
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
        <typeAlias type="com.longg.pojo.User" alias="User"/>
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
        <package name="com.longg.pojo"/>
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
    <com.longg.mapper resource="com/longg/mapper/UserMapper.xml"/>
</mappers>
```

- 注册方式二：使用class文件绑定注册

```xml
<!-- 使用映射器接口实现类的完全限定类名 -->
<mappers>
  <mapper class="org.mybatis.builder.AuthorMapper"/>
  <mapper class="org.mybatis.builder.BlogMapper"/>
  <mapper class="org.mybatis.builder.PostMapper"/>
</mappers>

例：
<mappers>
    <mapper class="com.longg.mapper.UserMapper"/>
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
    <package name="com.longg.mapper"/>
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

## 5. 解决属性名与字段名不一致的问题

==【工程3：mybatis-03】==

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

==【工程4：mybatis-04】==

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

----

## 7. 分页

==【工程4：mybatis-04】==

思考：为什么要分页？

- 减少数据的处理量；



### 7.1 Limit分页的方法

- 传统使用Limit分页：

```sql
语法：select * from user limit startIndex,PageSize;
select * from user limit 2,3;
```

- 使用MyBatis实现分页，核心为SQL；

  - 接口

  ```java
  /**
    * 分页查询
    */
  List<User> getUserLimit(Map<String ,Integer> map);
  ```

  - mapper.xml

  ```cml
  <select id="getUserLimit" resultMap="UserMap" parameterType="map">
  	select * from user limit #{startIndex},#{pageSize};
  </select>
  ```

  - 测试

  ```java
  @Test
  public void getUserLimit(){
      SqlSession sqlSession = MybatisUtils.getSqlSession();
  
      UserMapper mapper = sqlSession.getMapper(UserMapper.class);
      HashMap<String, Integer> map = new HashMap<String, Integer>();
      map.put("startIndex",2);
      map.put("pageSize",3);
      List<User> users = mapper.getUserLimit(map);
      for (User user : users) {
          System.out.println(user);
      }
  
      sqlSession.close();
  }
  ```

### 7.2 RowBounds分页的方法

不再使用SQL实现分页，在Java层实现分页

- 接口

```xml
/**
  * 分页查询:RowBounds
  */
List<User> getUserRowBounds();
```

- mapper.xml

```xml
<select id="getUserRowBounds" resultMap="UserMap" >
    select * from user ;
</select>
```

- 测试

```java
@Test
public void getUserRowBounds(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();

    // RowBounds实现分页
    RowBounds rowBounds = new RowBounds(2, 3);

    List<User> users = sqlSession.selectList("com.longg.mapper.UserMapper.getUserRowBounds",null,rowBounds);

    for (User user : users) {
        System.out.println(user);
    }

    sqlSession.close();
}
```

### 7.3 分页插件

![image-20200925100614939](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200925100614939.png)

----

## 8. 注解开发

==【工程5：mybatis-05】==

### 8.1 面向接口编程

**关于接口的理解**

- 接口从更深层次的理解，应是定义(规范，约束)与实现(名实分离的原则)的分离。

- 接口的本身反映了系统设计人员对系统的抽象理解。

- 接口应有两类:
  - 第一类是对一个体的抽象，它可对应为一个抽象体(abstract class);
  - 第二类是对一个体某一方面的抽象，即形成一个抽象面(interface);
- 一个体有可能有多个抽象面。抽象体与抽象面是有区别的。

**三个面向的区别**

- **面向对象**是指，我们考虑问题时，以对象为单位，考虑它的属性及方法

- **面向过程**是指，我们考虑问题时，以一个具体的流程(事务过程)为单位，考虑它的实现

- **接口设计与非接口设计**是针对复用技术而言的，与面向对象(过程)不是一个问题

### 8.2 使用注解开发

1. 注解在接口上实现；

```java
public interface UserMapper {

    /**
     * 查询全部用户
     */
    @Select("select * from user")
    List<User> getUsers();

}
```

2. 需要在核心配置文件中绑定接口；

```xml
<!--绑定接口-->
<mappers>
    <mapper class="com.longg.mapper.UserMapper"/>
</mappers>
```

3. 测试使用

```java
@Test
public void getUsers(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();

    // 底层主要运用反射
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    List<User> users = mapper.getUsers();
    for (User user : users) {
        System.out.println(user);
    }

    sqlSession.close();
}
```



- #### **本质：**反射机制实现；

- #### **底层：**动态代理；



### ==MyBatis详细的执行流程：==

  ![image-20200925141244885](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200925141244885.png)

### 8.3 自动提交事务

​       **==在MyBatis工具类MyBatisUtils中的实例化SqlSession的getSqlSession()方法中加上  `true`  即可自动提交事务，不用在执行SQL语句之后再手动提交事务，但是通常情况下不推荐使用自动提交事务的方法；==**         **<u>可对比3.2-3.4节的代码</u>**

​	   **手动提交事务：**工具类和测试类

![image-20200925152011848](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200925152011848.png)

![image-20200925152055410](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200925152055410.png)

​		**自动提交事务：**工具类和测试类

![image-20200925152152735](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200925152152735.png)

![image-20200925152225844](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200925152225844.png)



### 8.4 使用注解实现CRUD开发

- ##### 编写接口,增加注解（增删改查）

```java
public interface UserMapper {

    /**
     * 使用注解查询全部用户
     */
    @Select("select * from user")
    List<User> getUsers();

    /**
     * 使用注解根据id查询用户，参数获取使用注解 @Param("userId")；
     * SQL语句中的 id = #{userId} 部分的“userId”参数由注解 @Param("userId")装配；
     * 如果方法存在多个参数，则在所有的参数前面使用注解 @Param(" ") 即可；
     */
    @Select("select * from user where id = #{userId};")
    User getUserById(@Param("userId") int id);

    /**
     * 使用注解插入对象
     */
    @Insert("insert into user(id,name,pwd) values (#{id},#{name},#{password})")
    int insertUser(User user);

    /**
     * 使用注解修改用户
     */
    @Update("update user set name = #{name},pwd = #{password} where id = #{id};")
    int updateUser(User user);

    /**
     * 使用注解删除用户
     */
    @Delete("delete from user where id = #{id};")
    int deleteUser(@Param("id") int id);

}
```

**关于@Param(" ")注解说明**

- 基本类型的参数或者String类型，需要加上；【如上例代码中的“int"类型】
- 引用类型不需要加；   【如上例中的”User"类型】
- 如果只有一个基本类型的话，可以忽略，但是建议加上；
- 如果方法存在多个参数，则在所有的参数前面使用注解 @Param(" ") 即可；
- 上述查询 SQL语句中的 id = #{userId} 部分的“userId”参数由注解 @Param("userId")装配；



- ##### 测试实现

```java
public class UserMapperTest {

    @Test
    public void getUsers(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        // 底层主要运用反射
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.getUsers();
        for (User user : users) {
            System.out.println(user);
        }

        sqlSession.close();
    }

    @Test
    public void getUserById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        System.out.println(mapper.getUserById(2));

        sqlSession.close();
    }

    @Test
    public void insertUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        System.out.println(mapper.insertUser(new User(8,"longlong","6666")));

        sqlSession.close();
    }

    @Test
    public void updateUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        System.out.println(mapper.updateUser(new User(8,"long","66668888")));

        sqlSession.close();
    }

    @Test
    public void deleteUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        System.out.println(mapper.deleteUser(4));

        sqlSession.close();
    }

}
```

- ##### 注意：必须将我们的接口类注册绑定到配置文件中，详见8.1节

----

## 9. LomBok

==【工程6：mybatis-06】==

​		**Lombok项目是一个Java库，它会自动插入编辑器和构建工具中，Lombok提供了一组有用的注释，用来消除Java类中的大量样板代码。仅五个字符(@Data)就可以替换数百行代码从而产生干净，简洁且易于维护的 Java 类。**

### 9.1 常用注解

- @Setter ：注解在类或字段，注解在类时为所有字段生成setter方法，注解在字段上时只为该字段生成setter方法。

- @Getter ：使用方法同上，区别在于生成的是getter方法。

- @ToString ：注解在类，添加toString方法。

- @EqualsAndHashCode： 注解在类，生成hashCode和equals方法。

- ==@NoArgsConstructor： 注解在类，生成无参的构造方法。==

- @RequiredArgsConstructor： 注解在类，为类中需要特殊处理的字段生成构造方法，比如final和被@NonNull注解的字段。

- ==@AllArgsConstructor： 注解在类，生成包含类中所有字段的构造方法。==

- ==@Data： 注解在类，生成无参构造方法、setter/getter、equals、canEqual、hashCode、toString方法，如为final属性，则不会为该属性生成setter方法。==【最重要】

- @Slf4j： 注解在类，生成log变量，严格意义来说是常量。

### 9.2 使用Lombok的步骤

1. 在 IDEA 中安装对应的插件
2. 在项目的pom文件中导入Lombok的jar包

```xml
<dependency>
  <groupId>org.projectlombok</groupId>
  <artifactId>lombok</artifactId>
  <version>1.18.12</version>
</dependency>
```

3. 在实体类上添加其注解

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;
    private String name;
    private String password;

}
```

![image-20200925163914693](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200925163914693.png)

----

## 10. 多对一情况处理

==【工程7：mybatis-07】==

**测试环境搭建**

1. 导入Lombok；
2. 新建实体类：Teacher、Student；

```java
@Data
public class Student {
    private int id;
    private String name;
    
    /**
     * 学生对应的老师对象
     */
    private Teacher teacher;
}
```

```java
@Data
public class Teacher {
    private int id;
    private String name;
}
```

3. 建立Mapper接口；

4. 建立Mapper.xml文件；

5. 在核心配置文件中绑定注册我们的mapper接口或文件；

6. 测试查询是否成功；

### 10.1 按照查询嵌套处理

**==类似于Sql中的子查询：==**

mapper.xml文件：

```xml
<select id="getStudent" resultMap="StudentAndTeacher">
    select * from student;
</select>

<resultMap id="StudentAndTeacher" type="Student">
    <result property="id" column="id"/>
    <result property="name" column="name"/>
    <!--
        复杂的属性需要单独处理
        对象：association
        集合：collection
    -->
    <association property="teacher" column="teacher_id" javaType="Teacher" select="getTeacher"/>
</resultMap>

<select id="getTeacher" resultType="Teacher">
    select * from teacher where id = #{teacher_id};
</select>
```

结果展示：

![image-20200927100608412](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200927100608412.png)

### 10.2 按照结果嵌套处理

**==类似于Sql中的嵌套查询：==**

mapper.xml文件：

```xml
<select id="getStudent2" resultMap="StudentAndTeacher2">
    select s.id sId, s.name sName, t.name tName
    from teacher t, student s
    where s.teacher_id = t.id;
</select>

<resultMap id="StudentAndTeacher2" type="Student">
    <result property="id" column="sId"/>
    <result property="name" column="sName"/>
    <!--
        复杂的属性需要单独处理
        对象：association
        集合：collection
    -->
    <association property="teacher" javaType="Teacher">
        <result property="name" column="tName"/>
    </association>
</resultMap>
```

结果展示：

![image-20200927100513651](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200927100513651.png)

-----

## 11. 一对多情况处理

==【工程8：mybatis-08】==

**测试环境搭建**

1. 导入Lombok；
2. 新建实体类：Teacher、Student；

```java
@Data
public class Student {
    private int id;
    private String name;
    private int teacherId;
}
```

```java
@Data
public class Teacher {
    private int id;
    private String name;

    /**
     * 一个老师拥有多个学生
     */
    private List<Student> students;
}
```

3. 建立Mapper接口；

4. 建立Mapper.xml文件；

5. 在核心配置文件中绑定注册我们的mapper接口或文件；

6. 测试查询是否成功；

### 11.1 按照结果嵌套查询

**==类似于Sql中的嵌套查询：==**

mapper.xml文件：

```xml
<select id="getTeacherStudent" resultMap="TeacherAndStudent">
    select s.id sId, s.name sName, t.name tName, t.id tId
    from teacher t, student s
    where s.teacher_id = t.id ;
</select>

<resultMap id="TeacherAndStudent" type="Teacher">
    <result column="tId" property="id"/>
    <result column="tName" property="name"/>
    <!--
        复杂的属性需要单独处理
        对象：association
        集合：collection
        javaType="": 为指定属性的类型
        ofType="": 为集合中的泛型信息
    -->
    <collection property="students" ofType="Student">
        <result property="id" column="sId"/>
        <result property="name" column="sName"/>
        <result property="teacherId" column="tId"/>
    </collection>
</resultMap>
```

结果展示：

![image-20200927111734270](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200927111734270.png)

### 11.2 按照查询嵌套查询

**==类似于Sql中的子查询：==**

mapper.xml文件：

```xml
<select id="getTeacherStudent2" resultMap="TeacherAndStudent2">
    select * from teacher;
</select>

<resultMap id="TeacherAndStudent2" type="Teacher">
    <result column="id" property="id"/>
    <result column="name" property="name"/>
    <!--
        复杂的属性需要单独处理
        对象：association
        集合：collection
        javaType="": 为指定属性的类型
        ofType="": 为集合中的泛型信息
    -->
    <collection property="students" javaType="ArrayList" ofType="Student" select="getStudentByTeacherId" column="id"/>
</resultMap>

<select id="getStudentByTeacherId" resultType="Student">
    select * from student where teacher_id = #{teacherId};
</select>
```

结果展示：

![image-20200927114738803](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200927114738803.png)



### 11.3 小结

1. 关联：association 【多对一】
2. 集合：collection    【一对多】
3. javaType & ofType
   - javaType 用来指定实体类中属性的类型；
   - ofType 用来指定映射到List或者集合中的pojo类型，泛型中的约束类型；

掌握MySQL的引擎、InnoDB底层原理、索引、索引优化

----

## 12. 动态SQL

==【工程9：mybatis-09】==

- #### **动态 SQL 是 MyBatis 的强大特性之一**！

==**什么是动态SQL：动态SQL就是指根据不同的条件生成不同的SQL语句；**==

如果你之前用过 JSTL 或任何基于类 XML 语言的文本处理器，你对动态 SQL 元素可能会感觉似曾相识。在 MyBatis 之前的版本中，需要花时间了解大量的元素。借助功能强大的基于 OGNL 的表达式，MyBatis 3 替换了之前的大部分元素，大大精简了元素种类，现在要学习的元素种类比原来的一半还要少。

- if
- choose (when, otherwise)
- trim (where, set)
- foreach

**搭建环境**

```sql
CREATE TABLE blog
(
	id varchar(50) NOT NULL COMMENT '博客ID',
	title varchar(100) NOT NULL COMMENT '博客标题',
    author varchar(30) NOT NULL COMMENT '博客作者',
    create_time datetime NOT NULL COMMENT '创建时间',
    views int(30) NOT NULL COMMENT '浏览量'
)engine=InnoDB default charset=utf8;
```

**创建基础工程：**

1. 导入Lombok；
2. 新建实体类：Blog；

```java
@Data
public class Blog {
    private int id;
    private String title;
    private String author;
    private Date createTime;
    private int views;
}
```

3. 建立Mapper接口；

4. 建立Mapper.xml文件；

   ````xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE mapper
           PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <mapper namespace="com.longg.mapper.BlogMapper">
   
   
   </mapper>
   ````

5. 在核心配置文件中绑定注册我们的mapper接口或文件；

### 12.1 IF

官网：

![image-20200927170736261](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200927170736261.png)

mapper.xml：

```xml
<select id="queryBlogIF" parameterType="map" resultType="Blog">
    select * from blog where 1=1	/*写 1=1 仅仅只为了拼接and语句*/
    <if test="title != null">
    	and title = #{title}
    </if>
    <if test="author != null">
    	and author = #{author}
    </if>
</select>
```

测试类：

```java
@Test
public void queryBlogIF(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();

    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

    HashMap map = new HashMap();
    map.put("title","Java如此简单");
    map.put("author","long");

    List<Blog> blogs = mapper.queryBlogIF(map);
    for (Blog blog : blogs) {
        System.out.println(blog);
    }

    sqlSession.close();
}
```

结果：

![image-20200927183655328](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200927183655328.png)

注释map.put("title","Java如此简单");的结果：

![image-20200927183737539](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200927183737539.png)

### 12.2 choose (when, otherwise)

有时候，我们不想使用所有的条件，而只是想从多个条件中选择一个使用。针对这种情况，MyBatis 提供了` choose` 元素，它有点像 Java 中的 switch 语句。

mapper.xml：

```xml
<select id="queryBlogChoose" resultType="Blog" parameterType="map">
    select * from blog where 1=1
    <choose>
        <when test="title != null">
            and title = #{title}
        </when>
        <when test="author != null">
            and author = #{author}
        </when>
        <otherwise>
            and views = #{views}
        </otherwise>
    </choose>
</select>
```

测试类：

```java
/**
  * 测试动态sql的Choose标签
  */
@Test
public void queryBlogChoose(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();

    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

    HashMap map = new HashMap();
    //        map.put("title","Java如此简单");
    //        map.put("author","long");
    map.put("views",666);

    List<Blog> blogs = mapper.queryBlogChoose(map);
    for (Blog blog : blogs) {
        System.out.println(blog);
    }

    sqlSession.close();
}
```

当map同时给title和author两个参数时，只选择第一个 `when` 中的语句执行，结果如下：

![image-20200927184747072](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200927184747072.png)

当map只给title和author两个参数中的一个时，选择对应的 `when `中的语句执行，结果如下：

![image-20200927185035061](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200927185035061.png)

![image-20200927185058017](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200927185058017.png)

当map不给参数参数时，choose会选择执行最后的 `otherwise` 中的语句执行，结果如下：(如果不给views参数则查询为空)

![image-20200927185155849](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200927185155849.png)

![image-20200927185304638](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200927185304638.png)

### 12.3 trim (where, set)

- ##### `where ` 元素只会在子元素返回任何内容的情况下才插入 “where” 子句。而且，若子句的开头为 “AND” 或 “OR”，*`where`* 元素也会将它们去除。不需要用“1=1”来拼接where语句了

mapper.xml：

```xml
    <select id="queryBlogWhere" resultType="Blog" parameterType="map">
        select * from blog
        <where>
            <if test="title != null">
                and title = #{title}
            </if>
            <if test="author != null">
                and author = #{author}
            </if>
        </where>
    </select>
```

测试类：

```java
/**
  * 测试动态sql的where标签
  */
@Test
public void queryBlogWhere(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();

    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

    HashMap map = new HashMap();
    map.put("title","Java如此简单");
    map.put("author","long");

    List<Blog> blogs = mapper.queryBlogWhere(map);
    for (Blog blog : blogs) {
        System.out.println(blog);
    }

    sqlSession.close();
}
```

两个条件均成立的情况，结果如下：

![image-20200927190030213](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200927190030213.png)

两个条件均不成立的情况，结果如下：

![image-20200927190001951](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200927190001951.png)

- ##### 用于动态更新语句的类似于 `where` 标签的解决方案叫做  *`set`*。*`set`* 元素可以用于动态包含需要更新的列，忽略其它不更新的列;

mapper.xml：

```xml
<update id="updateBlogSet" parameterType="map">
    update blog
    <set>
        <if test="title != null">
            title = #{title},
        </if>
        <if test="author != null">
            author = #{author}
        </if>
    </set>
    where id = #{id}
</update>
```

测试类：

```java
/**
  * 测试动态sql的set标签
  */
@Test
public void updateBlogSet(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();

    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

    HashMap map = new HashMap();
    map.put("title","Java如此简单2");
    map.put("author","long2");
    map.put("id","ebceb80b40ef4027af8d6ff9a72c756e");

    mapper.updateBlogSet(map);

    sqlSession.close();
}
```

两个条件均成立的情况，结果如下：

![image-20200927190926987](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200927190926987.png)

只有第一个条件均成立的情况，结果如下：

![image-20200927190905987](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200927190905987.png)

- ##### 也可以通过自定义 trim 元素来定制 *`where`* 元素和 *`set`* 元素的功能

```xml
<trim prefix="WHERE" prefixOverrides="AND |OR ">
  ...
</trim>
```

```xml
<trim prefix="SET" suffixOverrides=",">
  ...
</trim>
```

### 12.4 Foreach

- ##### 动态 SQL 的另一个常见使用场景是对集合进行遍历（尤其是在构建 IN 条件语句的时候）

- ##### *`foreach`* 元素的功能非常强大，它允许你指定一个集合，声明可以在元素体内使用的集合项（item）和索引（index）变量。它也允许你指定开头与结尾的字符串以及集合项迭代之间的分隔符。这个元素也不会错误地添加多余的分隔符，看它多智能！

- ##### 你可以将任何可迭代对象（如 List、Set 等）、Map 对象或者数组对象作为集合参数传递给 *foreach*。当使用可迭代对象或者数组时，index 是当前迭代的序号，item 的值是本次迭代获取到的元素。当使用 Map 对象（或者 Map.Entry 对象的集合）时，index 是键，item 是值。

mapper.xml：（map传参）

```xml
<select id="queryBlogForeach" parameterType="map" resultType="Blog">
    select * from blog
    <where>
        <foreach collection="ids" item="id" open="(" close=")" separator="or">
            id = #{id}
        </foreach>
    </where>
</select>
```

测试类：

```java
/**
  * 测试使用Foreach
  */
@Test
public void queryBlogForeach(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();

    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

    HashMap map = new HashMap();

    ArrayList<Integer> ids = new ArrayList<Integer>();
    ids.add(1);
    ids.add(2);
    ids.add(3);

    map.put("ids",ids);

    List<Blog> blogs = mapper.queryBlogForeach(map);
    for (Blog blog : blogs) {
        System.out.println(blog);
    }

    sqlSession.close();
}
```

结果如下：

![image-20200927195424608](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200927195424608.png)

mapper.xml：（int数组传参）

```xml
<select id="queryBlogForeach" parameterType="int[]" resultType="Blog">
    select * from blog
    <where>
        <foreach collection="array" item="id" open="(" close=")" separator="or">
            id = #{id}
        </foreach>
    </where>
</select>
```

mapper.xml：（List传参）

```xml
<select id="queryBlogForeach" parameterType="list" resultType="Blog">
    select * from blog
    <where>
        <foreach collection="list" item="id" open="(" close=")" separator="or">
            id = #{id}
        </foreach>
    </where>
</select>
```

mapper.xml：（List传参, SQL为in语句）

```xml
<select id="queryBlogForeach" parameterType="list" resultType="Blog">
    select * from blog where id in
        <foreach collection="list" item="id" open="(" close=")" separator=",">
            #{id}
        </foreach>
</select>
```

### 12.5 SQL片段

- ##### 有时候，我们可能会将以一些功能的部分抽取出来，实现SQL的复用；

- ##### ==用法：==使用SQL标签抽取公共部分，在需要使用的地方使用include标签引用即可；

- ##### 注意事项：最好基于单表来定义SQL片段；不要存在where标签；

mapper.xml：

```xml
<select id="queryBlogSQL" resultType="Blog" parameterType="map">
    select * from blog
    <where>
        <include refid="if-title-author"></include>
    </where>
</select>

<sql id="if-title-author">
    <if test="title != null">
        and title = #{title}
    </if>
    <if test="author != null">
        and author = #{author}
    </if>
</sql>
```

测试类：

```java
/**
  * 测试使用动态SQL片段拼接SQL语句
  */
@Test
public void queryBlogSQL(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();

    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

    HashMap map = new HashMap();
    map.put("title","Java如此简单");
    map.put("author","long");

    List<Blog> blogs = mapper.queryBlogSQL(map);
    for (Blog blog : blogs) {
        System.out.println(blog);
    }

    sqlSession.close();
}
```

结果如下：

![image-20200927192508464](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200927192508464.png)

----

## 13. 缓存

### 13.1 MyBatis缓存

- MyBatis 内置了一个强大的事务性查询缓存机制，它可以非常方便地配置和定制； 
- 为了使它更加强大而且易于配置，我们对 MyBatis 3 中的缓存实现进行了许多改进；
- MyBatis系统中默认定义了两级缓存：一级缓存和二级缓存；
  - 默认情况下，只启用了本地的会话缓存（也叫一级缓存，是SqlSession级别的缓存），它仅仅对一个会话中的数据进行缓存；
  - 二级缓存需要手动开启配置，是基于namespace级别的缓存；
  - 为了提高扩展性，MyBatis定义了缓存接口Cache；我们可以通过实现Cache接口来自定义二级缓存；

### 13.2 一级缓存

- 一级缓存也叫本地缓存：SqlSession
  - 与数据库同一次会话期间查询到的数据会放在本地缓存中；
  - 如果需要获取相同的数据，直接从缓存中拿走即可，没必要再到数据库中查询；

- 测试：开启日志，测试在一个session中查询两次相同的记录，查看日志输出情况；

![image-20200928134104441](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200928134104441.png)

- 缓存失效的情况：

  - 查询不同的数据；
  - 增删改操作可能会改变数据库中原来的数据，所以必定会刷新缓存；

  ![image-20200928135032977](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200928135032977.png)

  - 查询不同的mapper.xml；
  - 手动清理缓存；；

```java
sqlSession.clearCache();    // 手动清理缓存
```

![image-20200928135637423](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200928135637423.png)

- 小结：一级缓存默认是开启的只在一次SqlSession中有效，也就是拿到连接到关闭连接的这个过程有效；

### 13.3 二级缓存

- 二级缓存也叫全局缓存，一级缓存的作用域太低了，所以诞生了二级缓存；

- 基于namespace级别的缓存，一个命名空间对应一个二级缓存；

- 工作机制：

  - 一个会话查询一条数据，这个数据就会被放在当前会话的以及缓存中；
  - 如果当前会话关闭了，这个会话对应的一级缓存就没有了，但是我们想要的是，会话关闭时对应一级缓存中的数据被保存到二级缓存中；
  - 新的会话查询信息，就可以从二级缓存中获取内容；
  - 不同的mapper查出的数据会放在自己对应的缓存中；

- 步骤：

  - 开启全局缓存；

  ```xml
  <!--显示的开启全局缓存-->
  <setting name="cacheEnabled" value="true"/>
  ```

  - 在mapper.xml中开启二级缓存

  ```xml
  <!--开启二级缓存-->
  <cache/>
  <!--自定义二级缓存-->
  <cache eviction="FIFO" flushInterval="60000" size="512" readOnly="true"/>
  ```

  - 测试

  ![image-20200928143656289](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200928143656289.png)

- 注意：我们需要将实体类序列化！否则可能会报错！

```java
Caused by: java.io.NotSerializableException: com.longg.pojo.User
```

​		**序列化的方法：**

![image-20200928143936717](C:\Users\A80024\AppData\Roaming\Typora\typora-user-images\image-20200928143936717.png)

- 小结：
  - 只要开启了二级缓存，在同一个mapper下就会有效；
  - 所有的数据都会先放在一级缓存中；
  - 只有当会话提交，或者关闭的时候，才会被提交到二级缓存中；

### 13.4 缓存的原理【重点】

- 一级缓存只要Sqlsession会话建立即随之建立，SqlSession在数据库中查询的书籍会自动保存在一级缓存中，第二次查询的时候直接在一级缓存中取出即可，在SqlSession被关闭的时候一级缓存也就随之关闭了；【一级缓存对应SqlSession】
- 二级缓存在同一个mapper下都能生效，再SqlSession建立时所有的查询数据都会先放在一级缓存中，只有当会话被关闭的时候，一级缓存内的数据才能被保存到二级缓存中，之后的查询都能在二级缓存中去取对应的数据；【二级缓存对应一个mapper】
- 数据存放的顺序：数据库、一级缓存、二级缓存；
- 用户查询缓存的顺序：用户先在二级缓存中是否有，没有则进入一级缓存中找是否有，最后才进入数据库中查询；【与数据存放的顺序相反】

![img](https://img-blog.csdnimg.cn/20200623165404113.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0RERERlbmdf,size_16,color_FFFFFF,t_70#pic_center)

### 13.5 自定义缓存（EhCache）

**==EhCache 是一个纯Java的进程内缓存框架，具有快速、精干等特点，是Hibernate中默认的CacheProvider。==**

















