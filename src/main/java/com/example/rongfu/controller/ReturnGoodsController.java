package com.example.rongfu.controller;

import com.example.rongfu.entity.Delivery;
import com.example.rongfu.entity.Purchase;
import com.example.rongfu.entity.ReturnGoods;
import com.example.rongfu.service.IReturnGoodsService;
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
@RequestMapping("/return")
public class ReturnGoodsController extends BaseController {

    @Autowired
    private IReturnGoodsService returnGoodsService;

    @RequestMapping("/all")
    public JsonResult<List<ReturnGoods>> all(@RequestBody String returnStr, HttpSession session) {
        ReturnGoods returnGoods = JsonUtils.json2ReturnGoods(returnStr);
        int userId = 0;
        if (session != null)
            userId = Integer.valueOf(session.getAttribute("userId").toString());
        else userId = returnGoods.getUserId();
        return new JsonResult<>(OK, returnGoodsService.all(userId));
    }

    @RequestMapping("/add")
    public JsonResult<Void> add(@RequestBody String returnStr, HttpSession session) {
        ReturnGoods returnGoods = JsonUtils.json2ReturnGoods(returnStr);
        if (session != null)
            returnGoods.setUserId(Integer.valueOf(session.getAttribute("userId").toString()));
        try {
            returnGoods.setAddTime(DateUtils.getDate(returnGoods.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new FailedException("未知错误，请联系管理员！");
        }
        returnGoodsService.add(returnGoods);
        return new JsonResult<>(OK);
    }
}
