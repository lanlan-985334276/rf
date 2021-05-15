package com.example.rongfu.mapper;

import com.example.rongfu.entity.Customer;
import com.example.rongfu.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CustomerMapper {

    @Insert("insert into customer(cname,photo,address,phone,epid,content,addtime,contact) " +
            "values(#{cName},#{photo},#{address},#{phone},#{epid},#{content},#{addTime},#{contact})")
    Integer insert(Customer customer);

    @Update("update customer set cname=#{cName}," +
            "photo=#{photo}," +
            "addTime=#{addTime},address=#{address}," +
            "phone=#{phone},contact=#{contact}," +
            "content=#{content},epid=#{epid} where customerid=#{customerId}")
    Integer update(Customer customer);

    @Delete("delete from customer where customerid=#{customerId}")
    Integer delete(int customerId);

    @Select("select * from customer where epid=#{epid}")
    List<Customer> findAll(int epid);

    @Select("select * from customer where cname like cName")
    Product findByPName(String cName);

    @Select("select * from customer where productid=#{productId}")
    Product findByProductId(int productId);

    @Select("select * from customer where customerid=#{customerId}")
    Customer findById(int customerId);
}
