package com.example.rongfu.controller;

import com.example.rongfu.entity.*;
import com.example.rongfu.mapper.*;
import com.example.rongfu.service.ex.FailedException;
import com.example.rongfu.util.JsonResult;
import com.example.rongfu.util.JsonUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/index")
public class HomeController extends BaseController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private PurchaseMapper purchaseMapper;
    @Autowired
    private DeliveryMapper deliveryMapper;
    @Autowired
    private ReturnGoodMapper returnGoodMapper;
    @Autowired
    private ProductMapper productMapper;

    @RequestMapping("/getUser")
    public JsonResult<User> getUser(@RequestBody String userStr, HttpSession session) {
        int userid = JsonUtils.json2User(userStr).getUserId();
        if (session != null && session.getAttribute("userId") != null) {
            userid = Integer.valueOf(session.getAttribute("userId").toString());
        }
        User user = userMapper.findByUserId(userid);
        if (user == null)
            throw new FailedException("未知错误！");
        Enterprise enterprise = enterpriseMapper.findByUserId(user.getUserId());
        Admin admin = adminMapper.findByUserId(user.getUserId());
        Staff staff = staffMapper.findByUserId(user.getUserId());
        if (enterprise != null) user.setEnterpriseId(enterprise.getEpId());
        if (admin != null) {
            System.out.println(admin);
            user.setAdminId(admin.getAdminId());
            user.setEnterpriseId(admin.getEpId());
        }
        if (staff != null) {
            System.out.println(staff);
            user.setStaffId(staff.getStaffId());
            user.setEnterpriseId(staff.getEpId());
        }
        //获取所有未完成的计划

        return new JsonResult<>(OK, user);
    }

    @RequestMapping("/summary")
    public JsonResult<Summary> summary(@RequestBody String userStr, HttpSession session) {
        int userid = 0;
        if (session != null) {
            userid = Integer.valueOf(session.getAttribute("userId").toString());
            if (userid == 0)
                throw new FailedException("未知错误！");
        } else {
            userid = JsonUtils.json2User(userStr).getUserId();
        }
        Enterprise enterprise = enterpriseMapper.findByUserId(userid);
        Admin admin = adminMapper.findByUserId(userid);
        int epid = 0;
        if (enterprise != null) epid = enterprise.getEpId();
        else if (admin != null) epid = admin.getAdminId();
        else throw new FailedException("您没有权限查看账单！");
        List<Purchase> purchases = purchaseMapper.findAll(epid);
        List<ReturnGoods> returnGoods = returnGoodMapper.findAll(epid);
        List<Delivery> deliveries = deliveryMapper.findAll(epid);
        int purchaseNum = 0;
        int returnGoodsNum = 0;
        int deliveryNum = 0;
        for (Purchase p : purchases) {
            purchaseNum += productMapper.findByProductId(p.getProductId()).getPrice() * p.getCount();
        }
        for (ReturnGoods r : returnGoods) {
            returnGoodsNum += productMapper.findByProductId(r.getProductId()).getPrice() * r.getCount();
        }
        for (Delivery d : deliveries) {
            deliveryNum += productMapper.findByProductId(d.getProductId()).getPrice() * d.getCount();
        }
        Summary summary = new Summary();
        summary.setPurchase(purchaseNum);
        summary.setReturnGoods(returnGoodsNum);
        summary.setDelivery(deliveryNum);
        return new JsonResult<>(OK, summary);
    }
}
