import com.longg.mapper.UserMapper;
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
