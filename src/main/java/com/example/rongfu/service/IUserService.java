package com.example.rongfu.service;


import com.example.rongfu.entity.User;

import java.util.List;

/**
 * 处理用户业务的接口
 */
public interface IUserService {

    /**
     * 用户注册
     *
     * @param username
     * @param password
     * @param code
     * @param eqName
     */
    void reg(String username, String password, String code, String eqName);

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录用户的信息
     */
    User login(String username, String password);

    void sendCode(String username);

    List<User> queryUserToAdd(String username);

    void addStaff(int mUserId, int userId);

    void deleteStaff(int userId);

    List<User> queryStaff(int userId);
    List<User> queryStaff2(int userId,String username);
}
