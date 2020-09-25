package com.longg.pojo;

import lombok.Data;

/**
 * @author long
 * @date 2020/9/25
 */
@Data
public class Student {
    private int id;
    private String name;
    private Teacher teacher;
}
