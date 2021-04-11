package com.example.rongfu.mapper;

import com.example.rongfu.entity.Staff;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StaffMapper {

    @Insert("insert into staff(userid,epid,addtime) values(userId,epId,addTime)")
    Integer insert(Staff staff);

    /*@Select("select * ")
    List<Staff> findNotInEnterprise(int epId);*/


}
