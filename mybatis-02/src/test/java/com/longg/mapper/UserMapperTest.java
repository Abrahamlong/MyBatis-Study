package com.longg.mapper;

import com.longg.pojo.User;
import com.longg.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

/**
 * @author long
 * @date 2020/9/16
 */
public class UserMapperTest {
    @Test
    public void test(){

        /**
         * 第一步：获得SqlSession 的对象
         */
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        /**
         * 第二步：执行sql
         * 方式一：getMapper（官方推荐）
         */
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = mapper.getUserList();

        /**
         * 方式二: 官方不推荐
         */
//        List<User> userList2 = sqlSession.selectList("com.com.longg.dao.UserMapper.getUserList");

        for (User user : userList) {
            System.out.println(user);
        }

        /**
         * 关闭SqlSession
         */
        sqlSession.close();
    }

    /**
     * 测试各种配置的新增使用  对应 4.配置解析
     */
    @Test
    public void getUserById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.getUserById(1);
        System.out.println(user);

        sqlSession.close();
    }

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

    @Test
    public void updateUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.updateUser(new User(4,"hhh","654321"));

        sqlSession.commit();

        sqlSession.close();
    }

    @Test
    public void deleteUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.deleteUser(6);

        sqlSession.commit();

        sqlSession.close();
    }


}
