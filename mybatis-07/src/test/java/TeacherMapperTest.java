import com.longg.mapper.TeacherMapper;
import com.longg.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

/**
 * @author long
 * @date 2020/9/25
 */
public class TeacherMapperTest {

    @Test
    public void getTeacher(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        System.out.println(mapper.getTeacher(1));

        sqlSession.close();
    }
}
