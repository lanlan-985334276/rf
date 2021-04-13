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

    @Insert("insert into sign(staffid,date) values(#{staffId},#{date})")
    Integer insert(SignIn signIn);

    @Select("select user.*,sign_in.date from staff,sign_in,user where staff.epid=#{epid} AND staff.staffid=sign_in.staffid AND sign_in.date>#{startTime} and sign_in.date <#{endTime} and staff.userid=user.userid")
    List<User> findTodaySignIn(int epid, Timestamp startTime, Timestamp endTime);

    @Select("select user.* from staff,sign_in,user where staff.epid=#{epid} and staff.userid=user.userid AND sign_in.date>#{startTime} and sign_in.date <#{endTime} and staff.staffid not in(sign_in.staffid)")
    List<User> findToadyNotSignIn(int epid, Timestamp startTime, Timestamp endTime);

    @Select("select user.*,sign_in.date from user,staff,sign_in where sign_in.staffid=staff.staffid and staff.epid=1 and staff.userid=user.userid")
    List<User> findAllByEpId(int epid);
}
