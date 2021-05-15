package com.example.rongfu.mapper;

import com.example.rongfu.entity.Plan;
import com.example.rongfu.entity.PlanItem;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface PlanMapper {

    @Insert("insert into plan(title,content,addtime,progress,epid,userid) " +
            "values(#{title},#{content},#{addTime},#{progress},#{epId},#{userId})")
    Integer insert(Plan plan);

    @Delete("delete from plan where planid=#{planId}")
    Integer delete(int planId);

    @Update("update plan set title=#{title},content=#{content},addtime=#{addTime},staffs=#{staffs}," +
            "progress=#{progress},epid=#{epId},userid=#{userId}")
    Integer update(Plan plan);

    @Select("select * from plan where epid=#{epid}")
    List<Plan> findAll(int epid);


    @Select("select * from plan where addtime>=#{startTime} and addtime<=#{endTime} and epid=#{epid}")
    List<Plan> findByDate(int epid, Timestamp startTime, Timestamp endTime);

    @Select("select * from plan where title=#{title} and epid=#{epid}")
    Plan findByTitle(String title, int epid);

    @Select("select * from plan where planid=#{planId}")
    Plan findByPlanId(int planId);

    @Select("select * from plan_item where planid=#{planId}")
    List<PlanItem> findItem(int planId);

    @Insert("insert into plan_item(planid,userid,state) values(#{planId},#{userId},#{state})")
    Integer insertItem(PlanItem item);

    @Select("select * from plan_item where userid=#{userId} and state=0")
    List<PlanItem> findByUserId(int userId);

    @Update("update plan_item set state=1 where piid=#{piId}")
    Integer updateItem(int piId);

    @Update("update plan set progress=#{progress} where planid=#{planId}")
    Integer updateProgress(Plan plan);

    @Select("select * from plan_item where userid=#{userId} and planid=#{planId}")
    PlanItem findByUidAndPiId(Plan plan);

    @Select("select count(*) from plan_item where planid=#{planId} and state=0")
    Integer findNotCount(int planId);
    @Select("select count(*) from plan_item where planid=#{planId}")
    Integer findCount(int planId);
}
