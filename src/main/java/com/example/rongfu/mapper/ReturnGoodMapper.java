package com.example.rongfu.mapper;

import com.example.rongfu.entity.Delivery;
import com.example.rongfu.entity.Purchase;
import com.example.rongfu.entity.ReturnGoods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReturnGoodMapper {

    @Insert("insert into return_goods(productid,pname,epid,addtime,content,count,customerid,cname) values(#{productId},#{pName},#{epId},#{addTime},#{content},#{count},#{customerId},#{cname})")
    Integer insert(ReturnGoods returnGoods);

    @Select("select return_goods.*,product.pname from return_goods,product where return_goods.epid=#{epId} and return_goods.productid=product.productid")
    List<ReturnGoods> findAll(int epId);

    @Select("select sum(count) from return_goods where epid=#{epId}")
    Integer sum(int epId);
}
