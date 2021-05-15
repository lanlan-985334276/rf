package com.example.rongfu.mapper;

import com.example.rongfu.entity.LogEntity;
import com.example.rongfu.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LogMapper {

    @Insert("insert into log(title,content,userid,username,epid,addTime) values(#{title},#{content},#{userId},#{userName},#{epId},#{addTime})")
    Integer insert(LogEntity logEntity);

    @Delete("delete from log where logId=#{logId}")
    Integer delete(int logId);

    @Select("select * from log where epid=#{epId} ")
    List<LogEntity> findAll(int epId);
}
