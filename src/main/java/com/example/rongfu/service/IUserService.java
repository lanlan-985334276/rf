package com.example.rongfu.service;


import com.example.rongfu.entity.User;

import java.util.List;

/**
 * 处理用户业务的接口
 */
public interface IUserService {

    /**
     * 用户注册
     */
    void regEp(User user);

    User reg(User user);

    /**
     * 用户登录
     *
     * @return 登录用户的信息
     */
    User login(User user);

    User loginByCode(String userName,String code);

    String sendCode(String username);

    List<User> queryUserToAdd(String username);

    void addStaff(int mUserId, int userId);

    void deleteStaff(int userId);

    List<User> queryStaff(int userId);

    List<User> queryStaff2(int userId, String username);

    User findByUserId(int userId);

    User findByUserName(String userName);

    User equalsCode(User user);
    User loginApp(User user);
    User regOther(User user);
}
