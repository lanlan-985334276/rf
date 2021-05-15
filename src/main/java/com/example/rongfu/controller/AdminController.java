package com.example.rongfu.controller;

import com.example.rongfu.entity.Admin;
import com.example.rongfu.entity.User;
import com.example.rongfu.service.IAdminService;
import com.example.rongfu.service.ex.FailedException;
import com.example.rongfu.util.JsonResult;
import com.example.rongfu.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired
    private IAdminService adminService;


    @RequestMapping("/add")
    public JsonResult<Void> add(@RequestBody String adminStr, HttpSession session) {
        Admin admin = JsonUtils.json2Admin(adminStr);
        int mUserId = 0;
        if (session != null)
            mUserId = Integer.valueOf(session.getAttribute("userId").toString());
        admin.setmUserId(mUserId);
        adminService.add(mUserId, admin.getUserId());
        return new JsonResult<>(OK);
    }

    @RequestMapping("/delete")
    public JsonResult<Void> delete(@RequestBody String adminStr) {
        adminService.delete(JsonUtils.json2Admin(adminStr).getAdminId());
        return new JsonResult<>(OK);
    }

    @RequestMapping("/all")
    public JsonResult<List<User>> all(@RequestBody String adminStr,HttpSession session) {
        Admin admin = JsonUtils.json2Admin(adminStr);
        int mUserId = 0;
        if (session != null)
            mUserId = Integer.valueOf(session.getAttribute("userId").toString());
        else mUserId=admin.getmUserId();
        return new JsonResult<>(OK, adminService.all(mUserId));
    }

    @RequestMapping("/other")
    public JsonResult<List<User>> other(@RequestBody String adminStr,HttpSession session) {
        Admin admin = JsonUtils.json2Admin(adminStr);
        int mUserId = 0;
        if (session != null)
            mUserId = Integer.valueOf(session.getAttribute("userId").toString());
        else mUserId=admin.getmUserId();
        return new JsonResult<>(OK, adminService.other(mUserId));
    }
}
