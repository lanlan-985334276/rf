package com.example.rongfu.mapper;

import com.example.rongfu.entity.Admin;
import com.example.rongfu.entity.Staff;
import com.example.rongfu.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 处理用户数据的持久层接口
 */
//mapper注解，在Sprigboot项目启动时，该注解会自动生成对应接口的实现类，并自动
// 创建该接口实现类的一个对象交给Spring框架容器管理
@Mapper
public interface AdminMapper {

    @Select("select user.*,admin.adminId from user,admin where admin.epid=#{epid} and admin.userid=user.userid")
    List<User> findAll(int epid);

    @Select("select user.* from user,staff where staff.epid=(select epid from admin where userid=#{userId}) and staff.userid=user.userid and staff.userid not in(select userid from admin)")
    List<User> findOther(int userId);

    @Select("select * from admin where userid=#{userid}")
    Admin findByUserId(int userid);

    /**
     * 插入记录
     *
     * @param admin 用户数据
     * @return 受影响的行数
     */
    @Insert("insert into admin(userid,epid) values(#{userId},#{epId})")
    Integer insert(Admin admin);

    @Delete("delete from admin where adminid=#{adminId}")
    Integer delete(int adminId);
}
