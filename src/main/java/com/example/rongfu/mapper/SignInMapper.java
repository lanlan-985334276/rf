package com.example.rongfu.mapper;

import com.example.rongfu.entity.SignIn;
import com.example.rongfu.entity.Staff;
import com.example.rongfu.entity.User;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface SignInMapper {

    @Insert("insert into sign_in(staffid,date,epid) values(#{staffId},#{date},#{epid})")
    Integer insert(SignIn signIn);

    @Select("select user.*,sign_in.date from staff,user,sign_in where staff.userid=user.userid and sign_in.staffid=staff.staffid and sign_in.date>=#{startTime} and sign_in.date <=#{endTime} and sign_in.epid=#{epid} and sign_in.state=1")
    List<User> findSignInByEpidAndTime(int epid, Timestamp startTime, Timestamp endTime);

    @Select("select user.*,sign_in.date from staff,user,sign_in where staff.userid=user.userid and sign_in.staffid=staff.staffid and sign_in.date>=#{startTime} and sign_in.date <=#{endTime} and sign_in.epid=#{epid} and sign_in.state=0")
    List<User> findNotSignInByEpidAndTime(int epid, Timestamp startTime, Timestamp endTime);

    @Select("select user.*,sign_in.date,sign_in.state from staff,user,sign_in where sign_in.staffid=staff.staffid and sign_in.epid=#{epid} and staff.userid=user.userid")
    List<User> findAllByEpId(int epid);

    @Delete("delete from sign_in where staffid=#{staffId}")
    Integer deleteByStaffId(int staffId);

    /**
     * 查找某公司某个时间段的签到记录
     *
     * @param epid
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("select * from sign_in where epid=#{epid} and date>=#{startTime} and date <=#{endTime}")
    List<SignIn> findByEpidAndTime(int epid, Timestamp startTime, Timestamp endTime);

    @Update("update sign_in set state=1,date=#{date} where siid=#{siId}")
    Integer update(SignIn signIn);
}
