package com.example.rongfu.mapper;

import com.example.rongfu.entity.Delivery;
import com.example.rongfu.entity.Step;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StepMapper {

    @Insert("insert into step(userid,username,num,epid) values(#{userId},#{userName},#{num},#{epId})")
    Integer insert(Step step);

    @Select("select * from step where epid=#{epId} ORDER BY num DESC")
    List<Step> findAll(int epId);

    @Update("update step set num=#{num},username=#{userName} where userid=#{userId}")
    Integer update(Step step);

    @Select("select * from step where userid=#{userId}")
    Step findByUserId(int userId);

}
