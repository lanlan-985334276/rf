package com.example.rongfu.mapper;

import com.example.rongfu.entity.Staff;
import com.example.rongfu.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 处理用户数据的持久层接口
 */
//mapper注解，在Sprigboot项目启动时，该注解会自动生成对应接口的实现类，并自动
// 创建该接口实现类的一个对象交给Spring框架容器管理
@Mapper
public interface UserMapper {
    /**
     * 按用户名查询
     *
     * @param username 用户名
     * @return 查询到的用户记录（封装成User对象），若未找到，返回null
     */
    @Select("select * from user where username=#{username}")
    User findByUserName(String username);

    @Select("select * from user where phone=#{phone}")
    User findByPhone(String phone);

    @Select("select * from user where email=#{email}")
    User findByEmail(String email);

    @Select("select user.* from user where user.userid=#{userid}")
    User findByUserId(int userid);

    /**
     * 插入记录
     *
     * @param user 用户数据
     * @return 受影响的行数
     */
    @Insert("insert into user(username,password,sex,photo,phone,email,lastlogintime,status) " +
            "values(#{userName},#{password},#{sex},#{photo},#{phone},#{email},#{lastLoginTime},#{status})")
    Integer insert(User user);

    @Update("update user set lastLoginTime=#{lastLoginTime} where userid=#{userId}")
    Integer updateLastLoginTime(User user);

    @Delete("delete from user where userid=#{userid}")
    Integer delete(int userid);

    @Select("select * from user where userid not in(select userid from staff) and username like #{username}")
    List<User> findNotInEnterprise(String username);

    @Update("update user set username=#{userName},email=#{email},password=#{password} where userid=#{userId}")
    Integer updateUser(User user);

    @Select("select * from user where username like #{username}")
    List<User> findLikeUsername(String username);
}
