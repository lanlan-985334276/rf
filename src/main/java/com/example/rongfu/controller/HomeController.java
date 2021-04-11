package com.example.rongfu.controller;

import com.example.rongfu.entity.User;
import com.example.rongfu.mapper.UserMapper;
import com.example.rongfu.service.ex.FailedException;
import com.example.rongfu.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/index")
public class HomeController extends BaseController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/getuser")
    public JsonResult<User> getUser(HttpSession session) {
        int userid = Integer.valueOf(session.getAttribute("userId").toString());
        if (userid == 0)
            throw new FailedException("未知错误！");
        User user = userMapper.findByUserId(userid);
        if (user == null)
            throw new FailedException("未知错误！");
        return new JsonResult<>(OK, user);
    }
}
