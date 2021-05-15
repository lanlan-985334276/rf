package com.example.rongfu.controller;

import com.example.rongfu.entity.Admin;
import com.example.rongfu.entity.Purchase;
import com.example.rongfu.entity.User;
import com.example.rongfu.service.IAdminService;
import com.example.rongfu.service.IPurchaseService;
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
@RequestMapping("/purchase")
public class PurchaseController extends BaseController {

    @Autowired
    private IPurchaseService purchaseService;

    @RequestMapping("/all")
    public JsonResult<List<Purchase>> all(@RequestBody String purchaseStr, HttpSession session) {
        Purchase purchase = JsonUtils.json2Purchase(purchaseStr);
        int userId = 0;
        if (session != null)
            userId = Integer.valueOf(session.getAttribute("userId").toString());
        else userId = purchase.getUserId();
        return new JsonResult<>(OK, purchaseService.all(userId));
    }

    @RequestMapping("/add")
    public JsonResult<Void> add(@RequestBody String purchaseStr, HttpSession session) {
        Purchase purchase = JsonUtils.json2Purchase(purchaseStr);
        if (session != null)
            purchase.setUserId(Integer.valueOf(session.getAttribute("userId").toString()));
        try {
            purchase.setAddTime(DateUtils.getDate(purchase.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new FailedException("未知错误，请联系管理员！");
        }
        purchaseService.add(purchase);
        return new JsonResult<>(OK);
    }
}
