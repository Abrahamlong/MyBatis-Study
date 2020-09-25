package com.longg.mapper;

import com.longg.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author long
 * @date 2020/9/16
 */
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
