package com.example.rongfu.mapper;

import com.example.rongfu.entity.Staff;
import com.example.rongfu.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StaffMapper {

    @Insert("insert into staff(userid,epid,addtime) values(#{userId},#{epId},#{addTime})")
    Integer insert(Staff staff);

    /*@Select("select * ")
    List<Staff> findNotInEnterprise(int epId);*/

    @Delete("delete from staff where userid=#{userId}")
    Integer delete(int userId);

    @Select("select * from user where userid in(select userid from staff where epid=#{epid})")
    List<User> findByEnterpriseId(int epid);

    @Select("select * from user where userid in(select userid from staff where epid=#{epid}) and username like #{username}")
    List<User> findByEnterpriseId2(int epid, String username);
}
