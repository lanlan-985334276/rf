package com.example.rongfu.entity;

public class ProductCategory {
    private int pcId;
    private String pcName;
    private int epid;
    private int userId;

    public ProductCategory() {
    }

    public int getPcId() {
        return pcId;
    }

    public void setPcId(int pcId) {
        this.pcId = pcId;
    }

    public String getPcName() {
        return pcName;
    }

    public void setPcName(String pcName) {
        this.pcName = pcName;
    }

    public int getEpid() {
        return epid;
    }

    public void setEpid(int epid) {
        this.epid = epid;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "pcId=" + pcId +
                ", pcName='" + pcName + '\'' +
                ", epid=" + epid +
                ", userId=" + userId +
                '}';
    }
}
