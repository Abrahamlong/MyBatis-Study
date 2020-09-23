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
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = mapper.getUserList();

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

  **==1.  报错：org.apache.ibatis.binding.BindingException: Type interface com.longg.dao.UserMapper is not known to the MapperRegistry.==**

  ==**MapperRegistry：Mapper注册中心：每一个Mapper.xml都需要在Mabatis的核心配置文件中注册**==

  **==解决方法：在MyBatis的核心配置文件中添加以下代码==**

  ```xml
      <mappers>
          <mapper resource="com/longg/dao/UserMapper.xml"/>  
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
- 对象传递参数的情况下，可以直接在sql中取其对象的属性；【parameterType="Object"（com.longg.pojo.User）】；
- Map传递参数的情况下，可以直接在sql中取其对应的key值；【parameterType="map"】

**如果是多个无规则的参数则可以用Map或者是注解；**

### 模糊查询

**模糊查询怎么写？**

​	**1. 在java代码执行的时候传递通配符 % %；【List<User> users = mapper.getUserLike("%李%");】**

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







































