package com.example.rongfu.controller;

import com.example.rongfu.entity.SignIn;
import com.example.rongfu.entity.User;
import com.example.rongfu.service.ISignInService;
import com.example.rongfu.util.JsonResult;
import com.example.rongfu.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/signIn")
public class SignInController extends BaseController {

    @Autowired
    private ISignInService signInService;

    @RequestMapping("/todayNotSignIn")
    JsonResult<List<User>> todayNotSignIn(@RequestBody String userStr, HttpSession session) {
        int userId = 0;
        if (session != null)
            userId = Integer.valueOf(session.getAttribute("userId").toString());
        else userId = JsonUtils.json2User(userStr).getUserId();
        List<User> list = signInService.ToadyNotSignIn(userId);
        return new JsonResult<>(OK, list);
    }

    /**
     * 今日已签到
     *
     * @param userStr
     * @param session
     * @return
     */
    @RequestMapping("/todaySignIn")
    JsonResult<List<User>> todaySignIn(@RequestBody String userStr, HttpSession session) {
        int userId = 0;
        if (session != null)
            userId = Integer.valueOf(session.getAttribute("userId").toString());
        else userId = JsonUtils.json2User(userStr).getUserId();
        List<User> list = signInService.ToadySignIn(userId);
        return new JsonResult<>(OK, list);
    }

    @RequestMapping("/allSignInLog")
    JsonResult<List<User>> allSignInLog(@RequestBody String userStr, HttpSession session) {
        int userId = 0;
        if (session != null)
            userId = Integer.valueOf(session.getAttribute("userId").toString());
        else userId = JsonUtils.json2User(userStr).getUserId();
        List<User> list = signInService.findAll(userId);
        return new JsonResult<>(OK, list);
    }

    @RequestMapping("/signIn")
    JsonResult<Void> signIn(@RequestBody String signInStr) {
        SignIn signIn = JsonUtils.json2SignIn(signInStr);
        signInService.SignIn(signIn);
        return new JsonResult<>(OK);
    }
}
