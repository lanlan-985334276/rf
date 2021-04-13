package com.example.rongfu.mapper;

import com.example.rongfu.entity.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PCategoryMapper {

    @Insert("insert into product_category(pcname,userid) values(#{pcName},#{userId})")
    Integer insert(ProductCategory categroy);

    @Update("update product_category set pcname=#{pcName},userid=#{userId} where pcid=#{pcId}")
    Integer update(ProductCategory categroy);

    @Delete("delete from product_category where pcid=#{pcId}")
    Integer delete(ProductCategory category);

    @Select("select * from product_category where pcid=#{pcId} and userid=#{userId}")
    ProductCategory findByPcId(ProductCategory category);

    @Select("select * from product_category where userid=#{userId} and pcname=#{pcName}")
    ProductCategory findByPcName(ProductCategory category);

    @Select("select * from product_category where userid in(select userid from admin where epid=(select epid from admin where userid=#{userId}))")
    List<ProductCategory> findAll(int userId);
}
