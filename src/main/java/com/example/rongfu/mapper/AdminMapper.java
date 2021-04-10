package com.example.rongfu.mapper;

import com.example.rongfu.entity.Admin;
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
public interface AdminMapper {

    @Select("select * from admin where epid=#{epid}")
    Admin findByEpId(int epid);

    @Select("select * from admin where userid=#{userid}")
    Admin findByUserId(int userid);

    /**
     * 插入记录
     *
     * @param userid 用户数据
     * @return 受影响的行数
     */
    @Insert("insert into admin(userid) values(userid)")
    Integer insert(int userid);
}
