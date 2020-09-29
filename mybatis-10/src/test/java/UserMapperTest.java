import com.longg.mapper.UserMapper;
import com.longg.pojo.User;
import com.longg.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * @author long
 * @date 2020/9/28
 */
public class UserMapperTest {

    /**
     * 测试一级缓存
     */
    @Test
    public void testOneCache(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user1 = mapper.queryUserById(1);
        System.out.println(user1);

//        System.out.println("=====================================================");
//        mapper.updateUser(new User(3,"longlong","8888"));
        sqlSession.clearCache();    // 手动清理缓存

        System.out.println("=====================================================");
        User user2 = mapper.queryUserById(1);
        System.out.println(user2);
        System.out.println(user1==user2);

        sqlSession.close();
    }

    /**
     * 测试二级缓存
     */
    @Test
    public void testTwoCache(){
        SqlSession sqlSession1 = MybatisUtils.getSqlSession();
        SqlSession sqlSession2 = MybatisUtils.getSqlSession();

        UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
        System.out.println(mapper1.queryUserById(1));
        sqlSession1.close();

        UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
        System.out.println(mapper2.queryUserById(1));
        sqlSession2.close();
    }

}
