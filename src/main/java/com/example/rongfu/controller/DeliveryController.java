package com.example.rongfu.controller;

import com.example.rongfu.entity.Delivery;
import com.example.rongfu.entity.Purchase;
import com.example.rongfu.service.IDeliveryService;
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
@RequestMapping("/delivery")
public class DeliveryController extends BaseController {

    @Autowired
    private IDeliveryService deliveryService;

    @RequestMapping("/all")
    public JsonResult<List<Delivery>> all(@RequestBody String deliveryStr, HttpSession session) {
        Delivery delivery = JsonUtils.json2Delivery(deliveryStr);
        int userId = 0;
        if (session != null)
            userId = Integer.valueOf(session.getAttribute("userId").toString());
        else userId = delivery.getUserId();
        return new JsonResult<>(OK, deliveryService.all(userId));
    }

    @RequestMapping("/add")
    public JsonResult<Void> add(@RequestBody String deliveryStr, HttpSession session) {
        Delivery delivery = JsonUtils.json2Delivery(deliveryStr);
        if (session != null)
            delivery.setUserId(Integer.valueOf(session.getAttribute("userId").toString()));
        try {
            delivery.setAddTime(DateUtils.getDate(delivery.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new FailedException("未知错误，请联系管理员！");
        }
        deliveryService.add(delivery);
        return new JsonResult<>(OK);
    }
}
