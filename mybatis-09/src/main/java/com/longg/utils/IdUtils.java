package com.longg.utils;

import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * @author long
 * @date 2020/9/27
 */
@SuppressWarnings("all") // 抑制警告
public class IdUtils {

    public static String getId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    @Test
    public void test(){
        System.out.println(IdUtils.getId());
        System.out.println(IdUtils.getId());
        System.out.println(IdUtils.getId());
    }
}
