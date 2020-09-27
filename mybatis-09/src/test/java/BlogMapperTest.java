import com.longg.mapper.BlogMapper;
import com.longg.pojo.Blog;
import com.longg.utils.IdUtils;
import com.longg.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author long
 * @date 2020/9/27
 */
public class BlogMapperTest {

    /**
     * 插入数据到Blog表
     */
    @Test
    public void insertBlog(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        Blog blog = new Blog();
        blog.setId(IdUtils.getId());
        blog.setTitle("MyBatis如此简单");
        blog.setAuthor("long");
        blog.setCreateTime(new Date());
        blog.setViews(66);

        System.out.println(mapper.insertBlog(blog));

        blog.setId(IdUtils.getId());
        blog.setTitle("Java如此简单");
        blog.setViews(88);

        System.out.println(mapper.insertBlog(blog));

        blog.setId(IdUtils.getId());
        blog.setTitle("Spring从入门到放弃");
        blog.setViews(6);

        System.out.println(mapper.insertBlog(blog));

        blog.setId(IdUtils.getId());
        blog.setTitle("SpringBoot从入门到放弃");
        blog.setViews(99);

        System.out.println(mapper.insertBlog(blog));

        sqlSession.close();
    }

    /**
     * 测试动态sql的if标签
     */
    @Test
    public void queryBlogIF(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        HashMap map = new HashMap();
//        map.put("title","Java如此简单");
        map.put("author","long");

        List<Blog> blogs = mapper.queryBlogIF(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }

        sqlSession.close();
    }

    /**
     * 测试动态sql的Choose标签
     */
    @Test
    public void queryBlogChoose(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        HashMap map = new HashMap();
//        map.put("title","Java如此简单");
//        map.put("author","long");
        map.put("views",666);

        List<Blog> blogs = mapper.queryBlogChoose(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }

        sqlSession.close();
    }

    /**
     * 测试动态sql的where标签
     */
    @Test
    public void queryBlogWhere(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        HashMap map = new HashMap();
        map.put("title","Java如此简单");
        map.put("author","long");

        List<Blog> blogs = mapper.queryBlogWhere(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }

        sqlSession.close();
    }

    /**
     * 测试动态sql的set标签
     */
    @Test
    public void updateBlogSet(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        HashMap map = new HashMap();
        map.put("title","Java如此简单2");
        map.put("author","long2");
        map.put("id","ebceb80b40ef4027af8d6ff9a72c756e");

        mapper.updateBlogSet(map);

        sqlSession.close();
    }

    /**
     * 测试使用动态SQL片段拼接SQL语句
     */
    @Test
    public void queryBlogSQL(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        HashMap map = new HashMap();
        map.put("title","Java如此简单");
        map.put("author","long");

        List<Blog> blogs = mapper.queryBlogSQL(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }

        sqlSession.close();
    }

    /**
     * 测试使用Foreach
     */
    @Test
    public void queryBlogForeach(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        HashMap map = new HashMap();

        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        ids.add(2);
        ids.add(3);

        map.put("ids",ids);

        List<Blog> blogs = mapper.queryBlogForeach(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }

        sqlSession.close();
    }
}
