package com.example.rongfu.controller;

import com.example.rongfu.entity.User;
import com.example.rongfu.service.IAdminService;
import com.example.rongfu.service.ex.FailedException;
import com.example.rongfu.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
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
    public JsonResult<Void> add(int tUserId, HttpSession session) {
        int mUserId = Integer.valueOf(session.getAttribute("userId").toString());
        if (mUserId == 0)
            throw new FailedException("未知错误！");
        adminService.add(mUserId, tUserId);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/delete")
    public JsonResult<Void> delete(int adminId) {
        adminService.delete(adminId);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/all")
    public JsonResult<List<User>> all(HttpSession session) {
        int userid = Integer.valueOf(session.getAttribute("userId").toString());
        if (userid == 0)
            throw new FailedException("未知错误！");
        return new JsonResult<>(OK, adminService.all(userid));
    }

    @RequestMapping("/other")
    public JsonResult<List<User>> other(HttpSession session) {
        int userid = Integer.valueOf(session.getAttribute("userId").toString());
        if (userid == 0)
            throw new FailedException("未知错误！");
        return new JsonResult<>(OK, adminService.other(userid));
    }
}
