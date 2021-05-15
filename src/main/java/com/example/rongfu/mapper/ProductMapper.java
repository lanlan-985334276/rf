package com.example.rongfu.mapper;

import com.example.rongfu.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Insert("insert into product(pname,pcid,photo,addtime,price,content,epid,count) " +
            "values(#{pName},#{pcId},#{photo},#{addTime},#{price},#{content},#{epid},#{count})")
    Integer insert(Product product);

    @Update("update product set pname=#{pName}," +
            "photo=#{photo}," +
            "addTime=#{addTime},price=#{price}," +
            "content=#{content},pcid=#{pcId},epid=#{epid},count=#{count} where productid=#{productId}")
    Integer update(Product product);

    @Delete("delete from product where productid=#{productId}")
    Integer delete(int productId);

    @Select("select * from product where epid=#{epid}")
    List<Product> findAll(int epid);

    @Select("select * from product where pname like pName")
    Product findByPName(String pName);

    @Select("select * from product where productid=#{productId}")
    Product findByProductId(int productId);

    @Update("update product set count=#{count} where productid=#{productId}")
    Integer updateCount(int count, int productId);

    @Select("select * from product where epid=#{epId} and count>0")
    List<Product> findByCount(int epId);
}
