package com.example.rongfu.mapper;

import com.example.rongfu.entity.Customer;
import com.example.rongfu.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CustomerMapper {

    @Insert("insert into customer(cname,photo,address,phone,userid,content,addtime,contact) " +
            "values(#{cName},#{photo},#{address},#{phone},#{userId},#{content},#{addTime},#{contact})")
    Integer insert(Customer customer);

    @Update("update customer set cname=#{cName}," +
            "photo=#{photo}," +
            "addTime=#{addTime},address=#{address}," +
            "phone=#{phone},contact=#{contact}," +
            "content=#{content},userid=#{userId} where customerid=#{customerId}")
    Integer update(Customer customer);

    @Delete("delete from customer where customerid=#{customerId}")
    Integer delete(int customerId);

    @Select("select * from customer where userid in(select userid from admin where epid=(select epid from admin where userid=#{userId}))")
    List<Customer> findAll(int userId);

    @Select("select * from customer where cname like cName")
    Product findByPName(String cName);

    @Select("select * from customer where productid=#{productId}")
    Product findByProductId(int productId);
}
