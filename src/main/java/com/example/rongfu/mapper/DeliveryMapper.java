package com.example.rongfu.mapper;

import com.example.rongfu.entity.Delivery;
import com.example.rongfu.entity.Purchase;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeliveryMapper {

    @Insert("insert into delivery(productid,pname,epid,addtime,content,count,customerid,cname) values(#{productId},#{pName},#{epId},#{addTime},#{content},#{count},#{customerId},#{cname})")
    Integer insert(Delivery delivery);

    @Select("select delivery.*,product.pname from delivery,product where delivery.epid=#{epId} and delivery.productid=product.productid")
    List<Delivery> findAll(int epId);

    @Select("select sum(count) from delivery where epid=#{epId}")
    Integer sum(int epId);
}
