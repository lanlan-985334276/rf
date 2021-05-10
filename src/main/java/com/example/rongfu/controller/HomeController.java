package com.example.rongfu.controller;

import com.example.rongfu.entity.User;
import com.example.rongfu.mapper.UserMapper;
import com.example.rongfu.service.ex.FailedException;
import com.example.rongfu.util.JsonResult;
import com.example.rongfu.util.JsonUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/index")
public class HomeController extends BaseController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/getUser")
    public JsonResult<User> getUser(@RequestBody String userStr, HttpSession session) {
        int userid = 0;
        if (session != null) {
            userid = Integer.valueOf(session.getAttribute("userId").toString());
            if (userid == 0)
                throw new FailedException("未知错误！");
        } else {
            userid = JsonUtils.json2User(userStr).getUserId();
        }
        User user = userMapper.findByUserId(userid);
        if (user == null)
            throw new FailedException("未知错误！");
        return new JsonResult<>(OK, user);
    }
}
