package com.longg.mapper;

import com.longg.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @author long
 * @date 2020/9/16
 */
public interface UserMapper {

    /**
     * 根据ID查询用户
     */
    User getUserById(int id);

    /**
     * 分页查询:Limit
     */
    List<User> getUserLimit(Map<String ,Integer> map);

    /**
     * 分页查询:RowBounds
     */
    List<User> getUserRowBounds();
}
