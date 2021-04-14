package com.example.rongfu.controller;

import com.example.rongfu.entity.Customer;
import com.example.rongfu.entity.Product;
import com.example.rongfu.service.ICustomerService;
import com.example.rongfu.service.IUploadImgService;
import com.example.rongfu.service.ex.FailedException;
import com.example.rongfu.util.DateUtils;
import com.example.rongfu.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private IUploadImgService uploadImgService;

    @RequestMapping("/all")
    JsonResult<List<Customer>> allSignInLog(HttpSession session) {
        int userId = Integer.valueOf(session.getAttribute("userId").toString());
        List<Customer> list = customerService.findAll(userId);
        return new JsonResult<>(OK, list);
    }

    @RequestMapping("/add")
    JsonResult<Customer> add(Customer customer, String time, HttpSession session) {
        int userId = Integer.valueOf(session.getAttribute("userId").toString());
        customer.setUserId(userId);
        try {
            customer.setAddTime(DateUtils.getDate(time));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new FailedException("未知错误，请联系管理员！");
        }
        return new JsonResult<>(OK, customerService.add(customer));
    }

    @RequestMapping("/update")
    JsonResult<Customer> update(Customer customer, String time, HttpSession session) {
        int userId = Integer.valueOf(session.getAttribute("userId").toString());
        customer.setUserId(userId);
        try {
            customer.setAddTime(DateUtils.getDate(time));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new FailedException("未知错误，请联系管理员！");
        }
        return new JsonResult<>(OK, customerService.update(customer));
    }

    @RequestMapping("/delete")
    JsonResult<Void> delete(Customer customer, HttpSession session) {
        int userId = Integer.valueOf(session.getAttribute("userId").toString());
        customer.setUserId(userId);
        customerService.delete(customer);
        return new JsonResult<>(OK);
    }
}
