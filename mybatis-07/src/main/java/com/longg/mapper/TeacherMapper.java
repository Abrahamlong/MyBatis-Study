package com.longg.mapper;

import com.longg.pojo.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author long
 * @date 2020/9/25
 */
public interface TeacherMapper {

    @Select("select * from teacher where id = #{id}")
    Teacher getTeacher(@Param("id") int id);
}
