package com.longg.mapper;

import com.longg.pojo.User;
import com.longg.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * @author long
 * @date 2020/9/16
 */
public class UserMapperTest {

    /**
     * 测试解决实体类属性名与数据库字段名不一致的问题  对应 5.解决属性名与字段名不一致的问题
     */
    @Test
    public void getUserById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.getUserById(1);
        System.out.println(user);

        sqlSession.close();
    }


}
