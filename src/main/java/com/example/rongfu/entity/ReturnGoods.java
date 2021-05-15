package com.example.rongfu.entity;

import java.sql.Timestamp;

public class ReturnGoods {
    private int returnId;
    private int productId;
    private String pName;
    private int epId;
    private Timestamp addTime;
    private String content;
    private int count;
    private String time;
    private int userId;
    private int customerId;
    private String cname;

    public ReturnGoods() {
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public int getReturnId() {
        return returnId;
    }

    public void setReturnId(int returnId) {
        this.returnId = returnId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ReturnGoods{" +
                "returnId=" + returnId +
                ", productId=" + productId +
                ", pName='" + pName + '\'' +
                ", epId=" + epId +
                ", addTime=" + addTime +
                ", content='" + content + '\'' +
                ", count=" + count +
                ", time='" + time + '\'' +
                ", userId=" + userId +
                ", customerId=" + customerId +
                ", cname='" + cname + '\'' +
                '}';
    }
}
