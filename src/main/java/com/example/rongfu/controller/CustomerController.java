package com.example.rongfu.controller;

import com.example.rongfu.entity.Customer;
import com.example.rongfu.entity.Product;
import com.example.rongfu.service.ICustomerService;
import com.example.rongfu.service.IUploadImgService;
import com.example.rongfu.service.ex.FailedException;
import com.example.rongfu.util.DateUtils;
import com.example.rongfu.util.JsonResult;
import com.example.rongfu.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController extends BaseController {

    @Autowired
    private ICustomerService customerService;

    @RequestMapping("/all")
    JsonResult<List<Customer>> all(@RequestBody String userStr, HttpSession session) {
        int userId = 0;
        if (session != null)
            userId = Integer.valueOf(session.getAttribute("userId").toString());
        else userId = JsonUtils.json2User(userStr).getUserId();
        List<Customer> list = customerService.findAll(userId);
        return new JsonResult<>(OK, list);
    }

    @RequestMapping("/add")
    JsonResult<Customer> add(@RequestBody String customerStr, HttpSession session) {
        System.out.println(customerStr);
        Customer customer=JsonUtils.json2Customer(customerStr);
        int userId = 0;
        if (session != null)
            userId = Integer.valueOf(session.getAttribute("userId").toString());
        else userId = customer.getUserId();
        customer.setUserId(userId);
        try {
            customer.setAddTime(DateUtils.getDate(customer.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new FailedException("未知错误，请联系管理员！");
        }
        return new JsonResult<>(OK, customerService.add(customer));
    }

    @RequestMapping("/update")
    JsonResult<Customer> update(@RequestBody String customerStr, HttpSession session) {
        Customer customer=JsonUtils.json2Customer(customerStr);
        int userId = 0;
        if (session != null)
            userId = Integer.valueOf(session.getAttribute("userId").toString());
        else userId = customer.getUserId();
        customer.setUserId(userId);
        try {
            customer.setAddTime(DateUtils.getDate(customer.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new FailedException("未知错误，请联系管理员！");
        }
        return new JsonResult<>(OK, customerService.update(customer));
    }

    @RequestMapping("/delete")
    JsonResult<Void> delete(@RequestBody String customerStr, HttpSession session) {
        Customer customer=JsonUtils.json2Customer(customerStr);
        int userId = 0;
        if (session != null)
            userId = Integer.valueOf(session.getAttribute("userId").toString());
        else userId = customer.getUserId();
        customer.setUserId(userId);
        customerService.delete(customer);
        return new JsonResult<>(OK);
    }
}
