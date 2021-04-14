package com.example.rongfu.mapper;

import com.example.rongfu.entity.Plan;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface PlanMapper {

    @Insert("insert into plan(title,content,addtime,staffs,progress,epid,userid) " +
            "values(#{title},#{content},#{addTime},#{staffs},#{progress},#{epId},#{userId})")
    Integer insert(Plan plan);

    @Delete("delete from plan where planid=#{planId}")
    Integer delete(int planId);

    @Update("update plan set title=#{title},content=#{content},addtime=#{addTime},staffs=#{staffs}," +
            "progress=#{progress},epid=#{epId},userid=#{userId}")
    Integer update(Plan plan);

    @Select("select * from plan where epid=(select epid from admin where admin.userid=#{userId})")
    List<Plan> findAll(int userId);


    @Select("select * from plan where addtime>#{startTime} and addtime<#{endTime} and epid=(select epid from admin where admin.userid=#{userId})")
    List<Plan> findByDate(int userId, Timestamp startTime, Timestamp endTime);

}
