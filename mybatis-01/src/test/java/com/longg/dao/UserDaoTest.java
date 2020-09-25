package com.longg.dao;

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
public class UserDaoTest {

    /**
     * 测试使用Mybatis  对应 2.第一个MyBatis程序
     */
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
        List<User> userList2 = sqlSession.selectList("com.longg.dao.UserMapper.getUserList");

        for (User user : userList) {
            System.out.println(user);
        }

        /**
         * 关闭SqlSession
         */
        sqlSession.close();
    }

    /**
     * 测试使用根据id查询  对应 3.CRUD
     */
    @Test
    public void getUserById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.getUserById(1);
        System.out.println(user);

        sqlSession.close();
    }

    /**
     * 测试使用插入记录  对应 3.CRUD
     */
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

    /**
     * 测试使用修改用户  对应 3.CRUD
     */
    @Test
    public void updateUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.updateUser(new User(4,"hhh","654321"));

        sqlSession.commit();

        sqlSession.close();
    }

    /**
     * 测试使用删除数据  对应 3.CRUD
     */
    @Test
    public void deleteUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.deleteUser(6);

        sqlSession.commit();

        sqlSession.close();
    }

    /**
     * 测试使用Map  对应 3.CRUD
     */
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

    /**
     * 测试使用模糊查询  对应 3.CRUD
     */
    @Test
    public void getUserLike() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //List<User> users = mapper.getUserLike("%李%");
        List<User> userlist = mapper.getUserLike("龙");
        for (User user : userlist) {
            System.out.println(user);
        }

        sqlSession.close();
    }
}