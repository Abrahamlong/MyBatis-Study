import com.longg.mapper.TeacherMapper;
import com.longg.pojo.Teacher;
import com.longg.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * @author long
 * @date 2020/9/25
 */
public class TeacherMapperTest {

    @Test
    public void getTeacher(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        List<Teacher> teacherList = mapper.getTeacher();
        for (Teacher teacher : teacherList) {
            System.out.println(teacher);
        }

        sqlSession.close();
    }

    @Test
    public void getTeacherStudent1(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        List<Teacher> teacherList = mapper.getTeacherStudent1();
        for (Teacher teacher : teacherList) {
            System.out.println(teacher);
        }

        sqlSession.close();
    }

    @Test
    public void getTeacherStudent2(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        List<Teacher> teacherList = mapper.getTeacherStudent2();
        for (Teacher teacher : teacherList) {
            System.out.println(teacher);
        }

        sqlSession.close();
    }
}
