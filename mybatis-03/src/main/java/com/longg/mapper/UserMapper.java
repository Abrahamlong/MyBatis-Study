package com.longg.mapper;

import com.longg.pojo.User;

import java.util.List;

/**
 * @author long
 * @date 2020/9/16
 */
public interface UserMapper {

    /**
     * 根据ID查询用户
     */
    User getUserById(int id);

}
