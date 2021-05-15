package com.example.rongfu.mapper;

import com.example.rongfu.entity.Purchase;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PurchaseMapper {

    @Insert("insert into purchase(productid,pname,epid,addtime,content,count,customerid,cname) values(#{productId},#{pName},#{epId},#{addTime},#{content},#{count},#{customerId},#{cname})")
    Integer insert(Purchase purchase);

    @Select("select purchase.*,product.pname from purchase,product where purchase.epid=#{epId} and purchase.productid=product.productid")
    List<Purchase> findAll(int epId);

    @Select("select sum(count) from purchase where epid=#{epId}")
    Integer sum(int epId);
}
