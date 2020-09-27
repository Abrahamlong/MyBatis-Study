package com.longg.mapper;

import com.longg.pojo.Student;

import java.util.List;

/**
 * @author long
 * @date 2020/9/25
 */
public interface StudentMapper {

    /**
     * 查询所有学生的信息，以及对于老师的信息  方式一：按照查询嵌套处理
     */
    public List<Student> getStudent1();

    /**
     * 查询所有学生的信息，以及对于老师的信息  方式二：按照结果嵌套处理
     */
    public List<Student> getStudent2();

}
