package com.example.rongfu.mapper;

import com.example.rongfu.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Insert("insert into product(pname,pcid,photo,addtime,price,content,userid) " +
            "values(#{pName},#{pcId},#{photo},#{addTime},#{price},#{content},#{userId})")
    Integer insert(Product product);

    @Update("update product set pname=#{pName}," +
            "photo=#{photo}," +
            "addTime=#{addTime},price=#{price}," +
            "content=#{content},userId=#{userId} where productid=#{productId}")
    Integer update(Product product);

    @Delete("delete from product where productid=#{productId}")
    Integer delete(int productId);

    @Select("select * from product where userid in(select userid from admin where epid=(select epid from admin where userid=#{userId}))")
    List<Product> findAll(int userId);

    @Select("select * from product where pname like pName")
    Product findByPName(String pName);

    @Select("select * from product where productid=#{productId}")
    Product findByProductId(int productId);
}
