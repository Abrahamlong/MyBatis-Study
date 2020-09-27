package com.longg.mapper;

import com.longg.pojo.Blog;

import java.util.List;
import java.util.Map;

/**
 * @author long
 * @date 2020/9/27
 */
public interface BlogMapper {

    /**
     * 插入数据
     */
    int insertBlog(Blog blog);

    /**
     * 查询博客，测试使用 IF
     */
    List<Blog> queryBlogIF(Map map);

    /**
     * 查询博客，测试使用 where
     */
    List<Blog> queryBlogWhere(Map map);

    /**
     * 查询博客，测试使用 choose
     */
    List<Blog> queryBlogChoose(Map map);

    /**
     * 更新博客，测试使用set
     */
    int updateBlogSet(Map map);

    /**
     * 查询博客，测试使用SQL片段拼接SQL语句
     */
    List<Blog> queryBlogSQL(Map map);

    /**
     * 查询博客，测试使用Foreach来遍历查询-1-2-3号用户
     */
    List<Blog> queryBlogForeach(Map map);

}
