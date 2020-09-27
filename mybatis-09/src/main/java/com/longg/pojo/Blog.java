package com.longg.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author long
 * @date 2020/9/27
 */
@Data
public class Blog {
    private String id;
    private String title;
    private String author;
    private Date createTime;
    private int views;
}
