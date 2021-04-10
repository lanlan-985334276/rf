package com.example.rongfu.mapper;

import com.example.rongfu.entity.Enterprise;
import com.example.rongfu.entity.VerificationCode;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 处理用户数据的持久层接口
 */
//mapper注解，在Sprigboot项目启动时，该注解会自动生成对应接口的实现类，并自动
// 创建该接口实现类的一个对象交给Spring框架容器管理
@Mapper
public interface VerificationCodeMapper {
    /**
     * 按用户名查询
     *
     * @param username 用户名
     * @return 验证码数据
     */
    @Select("select * from user where username=#{username}")
    VerificationCode findByUsername(String username);

    /**
     * 插入记录
     *
     * @param verificationCode 验证码
     * @return 受影响的行数
     */
    @Insert("insert into enterprise(userid,epname,time) " +
            "values(#{userId},#{epName},#{time})")
    Integer insert(VerificationCode verificationCode);


    Integer delete(int vcid);
}
