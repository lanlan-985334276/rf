package com.example.rongfu.controller;

import com.example.rongfu.entity.Staff;
import com.example.rongfu.entity.User;
import com.example.rongfu.service.IUserService;
import com.example.rongfu.util.JsonResult;
import com.example.rongfu.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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

    private final String TAG = "UserController==";


    /**
     * 注册为企业
     *
     * @param userStr
     * @return
     */
    @RequestMapping("/regEp")
    JsonResult<Void> regEp(@RequestBody String userStr) {
        userService.regEp(JsonUtils.json2User(userStr));
        return new JsonResult<>(OK);
    }

    /**
     * 处理用户登录
     *
     * @return JsonResult对象
     */
    @RequestMapping("/login")
    JsonResult<User> loginWeb(@RequestBody String userStr, HttpSession session) {
        User data = userService.loginWeb(JsonUtils.json2User(userStr)); //ctrl+alt+t
        if (session != null) {
            session.setAttribute("userId", data.getUserId());
            session.setAttribute("username", data.getUserName());
            session.setAttribute("isEnterprise", data.isEnterprise());
            session.setAttribute("isAdmin", data.isAdmin());
        }
        return new JsonResult<>(OK, data);
    }

    @RequestMapping("/loginApp")
    JsonResult<User> loginApp(@RequestBody String userStr) {
        User data = userService.loginApp(JsonUtils.json2User(userStr)); //ctrl+alt+t
        return new JsonResult<>(OK, data);
    }

    @RequestMapping("/loginByCode")
    JsonResult<User> loginByCode(@RequestBody String userStr, HttpSession session) {
        User user = JsonUtils.json2User(userStr);
        User data = userService.loginByCode(user.getUserName(), user.getCode());
        if (session != null) {
            session.setAttribute("userId", data.getUserId());
            session.setAttribute("username", data.getUserName());
            session.setAttribute("isEnterprise", data.isEnterprise());
            session.setAttribute("isAdmin", data.isAdmin());
        }
        return new JsonResult<>(OK, data);
    }


    @RequestMapping("/sendCode")
    JsonResult<String> sendCode(@RequestBody String userStr) {
        return new JsonResult<>(OK, userService.sendCode(JsonUtils.json2User(userStr).getUserName()));
    }


    @RequestMapping("/queryUserToAdd")
    JsonResult<List<User>> queryUserToAdd(@RequestBody String userStr, HttpSession session) {
        User user = JsonUtils.json2User(userStr);
        if (session != null) user.setUserId(Integer.valueOf(session.getAttribute("userId").toString()));
        return new JsonResult<>(OK, userService.queryUserToAdd(user));
    }

    /**
     * 添加员工
     *
     * @param staffStr
     * @param session
     * @return
     */
    @RequestMapping("/addStaff")
    JsonResult<Void> addStaff(@RequestBody String staffStr, HttpSession session) {
        Staff staff = JsonUtils.json2Staff(staffStr);
        if (session != null)
            staff.setmUserId(Integer.valueOf(session.getAttribute("userId").toString()));
        userService.addStaff(staff.getmUserId(), staff.getUserId());
        return new JsonResult<>(OK);
    }

    /**
     * 根据用户ID删除员工
     *
     * @param userStr
     * @return
     */
    @RequestMapping("/deleteStaff")
    JsonResult<Void> deleteStaff(@RequestBody String userStr) {
        userService.deleteStaff(JsonUtils.json2User(userStr).getUserId());
        return new JsonResult<>(OK);
    }

    /**
     * 查找所有员工（根据当前登录的ID）
     *
     * @param userStr
     * @param session
     * @return
     */
    @RequestMapping("/queryAllStaff")
    JsonResult<List<User>> queryAllStaff(@RequestBody String userStr, HttpSession session) {
        User user = new User();
        if (session != null)
            user.setUserId(Integer.valueOf(session.getAttribute("userId").toString()));
        else user = JsonUtils.json2User(userStr);
        return new JsonResult<>(OK, userService.queryAllStaff(user.getUserId()));
    }

    /**
     * 查找所有员工（根据当前登录的ID及筛选条件）
     *
     * @param userStr
     * @param session
     * @return
     */
    @RequestMapping("/queryStaffByUserName")
    JsonResult<List<User>> queryStaffByUserName(@RequestBody String userStr, HttpSession session) {
        User user = JsonUtils.json2User(userStr);
        if (session != null)
            user.setUserId(Integer.valueOf(session.getAttribute("userId").toString()));
        return new JsonResult<>(OK, userService.queryStaffByUserName(user.getUserId(), user.getUserName()));
    }

    /**
     * 根据用户ID查找
     *
     * @param user
     * @return
     */
    @RequestMapping("/findByUserId")
    JsonResult<User> queryStaff(User user) {
        return new JsonResult<>(OK, userService.findByUserId(user.getUserId()));
    }

    /**
     * 根据用户名查找
     *
     * @param userName
     * @return
     */
    @RequestMapping("/findByUserName")
    JsonResult<User> findByUserName(String userName) {
        return new JsonResult<>(OK, userService.findByUserName(userName));
    }

    /**
     * 验证 验证码
     *
     * @param userStr
     * @return
     */
    @RequestMapping("/equalsCode")
    JsonResult<User> equalsCode(@RequestBody String userStr) {
        return new JsonResult<>(OK, userService.equalsCode(JsonUtils.json2User(userStr)));
    }

    /**
     * 注册为普通用户
     *
     * @param userStr
     * @return
     */
    @RequestMapping("/regOther")
    JsonResult<User> regOther(@RequestBody String userStr) {
        return new JsonResult<>(OK, userService.regOther(JsonUtils.json2User(userStr)));
    }
}
