import com.longg.mapper.StudentMapper;
import com.longg.pojo.Student;
import com.longg.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * @author long
 * @date 2020/9/27
 */
public class StudentMapperTest {

    @Test
    public void getStudent1(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();



        sqlSession.close();
    }

}
