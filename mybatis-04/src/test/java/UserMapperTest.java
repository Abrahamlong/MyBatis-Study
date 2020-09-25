import com.longg.mapper.UserMapper;
import com.longg.pojo.User;
import com.longg.utils.MybatisUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

/**
 * @author long
 * @date 2020/9/16
 */
public class UserMapperTest {

    /**
     * 测试标准日志输出 STDOUT_LOGGING 的使用   对应 6.日志
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
     * 测试Log4J的使用  对应 6.日志
     */
    @Test
    public void testlog4j(){
        Logger logger = Logger.getLogger(UserMapperTest.class);
        logger.info("info:进入了testlog4j");
        logger.debug("debug:进入了testlog4j");
        logger.error("error:进入了testlog4j");
    }

    /**
     * 测试Limit实现分页  对应 7.分页
     */
    @Test
    public void getUserLimit(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("startIndex",2);
        map.put("pageSize",3);
        List<User> users = mapper.getUserLimit(map);
        for (User user : users) {
            System.out.println(user);
        }

        sqlSession.close();
    }

    /**
     * 测试RowBounds实现分页   对应 7.分页
     */
    @Test
    public void getUserRowBounds(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        // RowBounds实现分页
        RowBounds rowBounds = new RowBounds(2, 3);

        List<User> users = sqlSession.selectList("com.longg.mapper.UserMapper.getUserRowBounds",null,rowBounds);

        for (User user : users) {
            System.out.println(user);
        }

        sqlSession.close();
    }

}
