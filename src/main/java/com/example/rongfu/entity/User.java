package com.example.rongfu.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class User {
    private int userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别 0:男 1:女
     */
    private int sex;
    /**
     * 头像
     */
    private String photo;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 上次登录时间
     */
    private Timestamp lastLoginTime;
    /**
     * 账号状态 0:离线 1:在线 2:注销
     */
    private int status;

    private boolean isAdmin;

    private boolean isEnterprise;

    private Timestamp date;

    private int adminId;
    private int staffId;

    private int state;
    /**
     * 用于验证码登录
     */
    private String code;

    private String eqName;

    public String getEqName() {
        return eqName;
    }

    public void setEqName(String eqName) {
        this.eqName = eqName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User() {
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isEnterprise() {
        return isEnterprise;
    }

    public void setEnterprise(boolean enterprise) {
        isEnterprise = enterprise;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", sex=" + sex +
                ", photo='" + photo + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", status=" + status +
                ", isAdmin=" + isAdmin +
                ", isEnterprise=" + isEnterprise +
                ", date=" + date +
                ", adminId=" + adminId +
                ", staffId=" + staffId +
                '}';
    }
}
