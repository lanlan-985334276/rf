package com.example.rongfu.mapper;

import com.example.rongfu.entity.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PCategoryMapper {

    @Insert("insert into product_category(pcname,epid) values(#{pcName},#{epid})")
    Integer insert(ProductCategory categroy);

    @Update("update product_category set pcname=#{pcName} where pcid=#{pcId}")
    Integer update(ProductCategory categroy);

    @Delete("delete from product_category where pcid=#{pcId}")
    Integer delete(ProductCategory category);

    @Select("select * from product_category where pcid=#{pcId} and epid=#{epid}")
    ProductCategory findByPcId(ProductCategory category);

    @Select("select * from product_category where epid=#{epid} and pcname=#{pcName}")
    ProductCategory findByPcName(ProductCategory category);

    @Select("select * from product_category where epid=#{epid}")
    List<ProductCategory> findAll(int epid);
}
