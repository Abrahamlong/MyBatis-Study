package com.longg.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author long
 * @date 2020/9/25
 */
@Data
public class Teacher {
    private int id;
    private String name;

    /**
     * 一个老师拥有多个学生
     */
    private List<Student> students;
}
