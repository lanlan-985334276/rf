package com.example.rongfu.entity;

import java.sql.Timestamp;

public class Staff {
    private int staffId;
    private int userId;
    private int epId;
    private Timestamp addTime;
    private int mUserId;

    public Staff() {
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

    public int getEpId() {
        return epId;
    }

    public void setEpId(int epId) {
        this.epId = epId;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    public int getmUserId() {
        return mUserId;
    }

    public void setmUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "staffId=" + staffId +
                ", userId=" + userId +
                ", epId=" + epId +
                ", addTime=" + addTime +
                ", mUserId=" + mUserId +
                '}';
    }
}
