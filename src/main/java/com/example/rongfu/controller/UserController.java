package com.example.rongfu.controller;

import com.example.rongfu.entity.User;
import com.example.rongfu.service.IUserService;
import com.example.rongfu.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;


//@Controller注解，SpringBoot环境加载时，会自动创建该控
//制器类的一个对象，交给Spring框架容器进行管理
//@Controller //@Controller+@ResponseBody
//@RequestMapping注解，写在控制器类上，表示该控制器处理
// 所有以指定值（/users）开头的请求
@RestController
@RequestMapping("/users")
public class UserController extends BaseController {
    @Autowired //取出Spring框架容器中的对象（IUserService实现类的对象）赋给下面的成员变量
    private IUserService userService;

    //@RequestMapping注解，写在方法上，表示该方法处理
    // 具体指定url值（/users/reg）的请求
    @RequestMapping("/register")
    //@ResponseBody该注解表示将方法的返回值作为响应体的内容（该注解会自动将
    //方法的返回值，先转换成json格式，再进行响应）
    //@ResponseBody
    JsonResult<Void> reg(String username, String password, String code, String eqName) {
        userService.reg(username, password, code, eqName);
        return new JsonResult<>(OK);
    }

    /**
     * 处理用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return JsonResult对象
     */
    @RequestMapping("/login")
//@ResponseBody
    JsonResult<User> login(String username, String password, HttpSession session) {
        User data = userService.login(username, password); //ctrl+alt+t
        //登录成功，保存登录用户信息供后续业务使用
        System.out.println(data);
        session.setAttribute("userId", data.getUserId());
        session.setAttribute("username", data.getUserName());
        session.setAttribute("isEnterprise", data.isEnterprise());
        session.setAttribute("isAdmin", data.isAdmin());
        return new JsonResult<>(OK, data);
    }


    @RequestMapping("/sendCode")
    JsonResult<Void> sendCode(String username) {
        userService.sendCode(username);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/queryUserToAdd")
    JsonResult<List<User>> queryUserToAdd(String username) {
        return new JsonResult<>(OK, userService.queryUserToAdd(username));
    }

    @RequestMapping("/addStaff")
    JsonResult<Void> addStaff(int userId, HttpSession session) {
        int mUserId = Integer.valueOf(session.getAttribute("userId").toString());
        userService.addStaff(mUserId, userId);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/deleteStaff")
    JsonResult<Void> deleteStaff(int userId) {
        userService.deleteStaff(userId);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/queryStaff")
    JsonResult<List<User>> queryStaff(String username, HttpSession session) {
        int userId = Integer.valueOf(session.getAttribute("userId").toString());
        return new JsonResult<>(OK, username!=null ? userService.queryStaff2(userId, username) : userService.queryStaff(userId));
    }
}
