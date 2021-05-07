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
    @RequestMapping("/regEp")
    //@ResponseBody该注解表示将方法的返回值作为响应体的内容（该注解会自动将
    //方法的返回值，先转换成json格式，再进行响应）
    //@ResponseBody
    JsonResult<Void> regEp(User user) {
        userService.regEp(user);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/reg")
    JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<>(OK);
    }

    /**
     * 处理用户登录
     *
     * @return JsonResult对象
     */
    @RequestMapping("/login")
//@ResponseBody
    JsonResult<User> login(User user, HttpSession session) {
        User data = userService.login(user); //ctrl+alt+t
        //登录成功，保存登录用户信息供后续业务使用
        System.out.println(data);
        if (session != null) {
            session.setAttribute("userId", data.getUserId());
            session.setAttribute("username", data.getUserName());
            session.setAttribute("isEnterprise", data.isEnterprise());
            session.setAttribute("isAdmin", data.isAdmin());
        }
        return new JsonResult<>(OK, data);
    }

    @RequestMapping("/loginApp")
    JsonResult<User> loginApp(User user) {
        User data = userService.loginApp(user); //ctrl+alt+t
        return new JsonResult<>(OK, data);
    }

    @RequestMapping("/loginByCode")
    JsonResult<User> loginByCode(String userName, String code, HttpSession session) {
        User data = userService.loginByCode(userName, code);
        if (session != null) {
            session.setAttribute("userId", data.getUserId());
            session.setAttribute("username", data.getUserName());
            session.setAttribute("isEnterprise", data.isEnterprise());
            session.setAttribute("isAdmin", data.isAdmin());
        }
        return new JsonResult<>(OK, data);
    }


    @RequestMapping("/sendCode")
    JsonResult<String> sendCode(User user) {
        System.out.println(user);
        return new JsonResult<>(OK, userService.sendCode(user.getUserName()));
    }


    @RequestMapping("/queryUserToAdd")
    JsonResult<List<User>> queryUserToAdd(String username) {
        return new JsonResult<>(OK, userService.queryUserToAdd(username));
    }

    @RequestMapping("/addStaff")
    JsonResult<Void> addStaff(int userId, int mUserId, HttpSession session) {
        if (session == null)
            mUserId = Integer.valueOf(session.getAttribute("userId").toString());
        userService.addStaff(mUserId, userId);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/deleteStaff")
    JsonResult<Void> deleteStaff(int userId) {
        userService.deleteStaff(userId);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/queryStaff")
    JsonResult<List<User>> queryStaff(User user, HttpSession session) {
        if (session != null)
            user.setUserId(Integer.valueOf(session.getAttribute("userId").toString()));
        return new JsonResult<>(OK, user.getUserName() != null ? userService.queryStaff2(user.getUserId(), user.getUserName()) : userService.queryStaff(user.getUserId()));
    }

    @RequestMapping("/findByUserId")
    JsonResult<User> queryStaff(User user) {
        return new JsonResult<>(OK, userService.findByUserId(user.getUserId()));
    }

    @RequestMapping("/findByUserName")
    JsonResult<User> findByUserName(String userName) {
        return new JsonResult<>(OK, userService.findByUserName(userName));
    }

    @RequestMapping("/equalsCode")
    JsonResult<User> equalsCode(User user) {
        return new JsonResult<>(OK, userService.reg(user));
    }

    @RequestMapping("/regOther")
    JsonResult<User> regOther(User user) {
        return new JsonResult<>(OK, userService.regOther(user));
    }
}
