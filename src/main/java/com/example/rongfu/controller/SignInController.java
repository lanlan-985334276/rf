package com.example.rongfu.controller;

import com.example.rongfu.entity.User;
import com.example.rongfu.service.ISignInService;
import com.example.rongfu.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/signIn")
public class SignInController extends BaseController {

    @Autowired
    private ISignInService signInService;

    @RequestMapping("/todayNotSILog")
    JsonResult<List<User>> todayNot(HttpSession session) {
        int userId = Integer.valueOf(session.getAttribute("userId").toString());
        List<User> list = signInService.ToadyNotSignIn(userId);
        return new JsonResult<>(OK, list);
    }

    @RequestMapping("/todayAllSILog")
    JsonResult<List<User>> todayAll(HttpSession session) {
        int userId = Integer.valueOf(session.getAttribute("userId").toString());
        List<User> list = signInService.ToadySignIn(userId);
        return new JsonResult<>(OK, list);
    }

    @RequestMapping("/allSignInLog")
    JsonResult<List<User>> allSignInLog(HttpSession session) {
        int userId = Integer.valueOf(session.getAttribute("userId").toString());
        List<User> list = signInService.findAll(userId);
        return new JsonResult<>(OK, list);
    }
}
