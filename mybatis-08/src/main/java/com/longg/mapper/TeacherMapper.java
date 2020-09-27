package com.longg.mapper;

import com.longg.pojo.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author long
 * @date 2020/9/25
 */
public interface TeacherMapper {

    /**
     * 获取所有老师
     */
    List<Teacher> getTeacher();

    /**
     * 获取所有老师下的所有学生及老师的信息  方式一：按照结果嵌套查询
     */
    List<Teacher> getTeacherStudent1();

    /**
     * 获取所有老师下的所有学生及老师的信息  方式二：按照查询嵌套查询
     */
    List<Teacher> getTeacherStudent2();
}
