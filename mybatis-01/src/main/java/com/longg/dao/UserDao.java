package com.longg.dao;

import com.longg.pojo.User;

import java.util.List;

/**
 * @author long
 * @date 2020/9/16
 */
// 以前的老方法，写实体类的UserDao接口，现在改成UserMapper接口
public interface UserDao {
    public List<User> getUserList();
}
