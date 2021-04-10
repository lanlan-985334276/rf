package com.example.rongfu.service;


import com.example.rongfu.entity.Enterprise;
import com.example.rongfu.entity.User;
import com.example.rongfu.entity.VerificationCode;

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
}
