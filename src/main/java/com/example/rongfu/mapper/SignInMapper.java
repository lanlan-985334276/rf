package com.example.rongfu.mapper;

import com.example.rongfu.entity.SignIn;
import com.example.rongfu.entity.Staff;
import com.example.rongfu.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface SignInMapper {

    @Insert("insert into sign_in(staffid,date) values(#{staffId},#{date})")
    Integer insert(SignIn signIn);

    @Select("select user.*,sign_in.date from staff,user,sign_in where staff.epid=#{epid} and staff.userid=user.userid and staff.staffid in(select staffid from sign_in where sign_in.date>#{startTime} and sign_in.date <#{endTime})")
    List<User> findTodaySignIn(int epid, Timestamp startTime, Timestamp endTime);

    @Select("select user.*,sign_in.date from staff,user,sign_in where staff.userid=user.userid and staff.staffid not in(select staffid from sign_in where sign_in.date>#{startTime} and sign_in.date <#{endTime} and staff.epid=#{epid})")
    List<User> findToadyNotSignIn(int epid, Timestamp startTime, Timestamp endTime);

    @Select("select user.*,sign_in.date from staff,user,sign_in where sign_in.staffid=staff.staffid and staff.epid=#{epid} and staff.userid=user.userid")
    List<User> findAllByEpId(int epid);

    @Delete("delete from sign_in where staffid=#{staffId}")
    Integer deleteByStaffId(int staffId);
}
