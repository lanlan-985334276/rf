package com.example.rongfu.entity;

public class Admin {
    private int adminId;
    private int userId;
    private int epId;
    private int mUserId;

    public int getmUserId() {
        return mUserId;
    }

    public void setmUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public Admin() {
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEpId() {
        return epId;
    }

    public void setEpId(int epId) {
        this.epId = epId;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", userId=" + userId +
                ", epId=" + epId +
                '}';
    }
}
