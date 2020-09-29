package com.longg.mapper;

import com.longg.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author long
 * @date 2020/9/28
 */
public interface UserMapper {

    /**
     * 根据id查询用户
     */
    User queryUserById(@Param("id") int id);

    /**
     * 修改用户
     */
    int updateUser(User user);
}
