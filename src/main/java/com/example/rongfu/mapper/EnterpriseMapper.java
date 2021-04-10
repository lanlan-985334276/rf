package com.example.rongfu.mapper;

import com.example.rongfu.entity.Enterprise;
import com.example.rongfu.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 处理用户数据的持久层接口
 */
//mapper注解，在Sprigboot项目启动时，该注解会自动生成对应接口的实现类，并自动
// 创建该接口实现类的一个对象交给Spring框架容器管理
@Mapper
public interface EnterpriseMapper {
    /**
     * 按用户名查询
     *
     * @param epName 用户名
     * @return 查询到的用户记录（封装成User对象），若未找到，返回null
     */
    @Select("select * from enterprise where epName=#{epName}")
    Enterprise findByName(String epName);

    /**
     * 插入记录
     *
     * @param enterprise 用户数据
     * @return 受影响的行数
     */
    @Insert("insert into enterprise(userid,epname,time) " +
            "values(#{userId},#{epName},#{time})")
    Integer insert(Enterprise enterprise);

    @Select("select * from enterprise where userid=#{userid}")
    Enterprise findByUserId(int userid);
}
