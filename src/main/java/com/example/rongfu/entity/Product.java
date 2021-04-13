package com.example.rongfu.entity;

import java.sql.Timestamp;

public class Product {
    private int productId;
    private String pname;
    private int pcId;
    private String photo;
    private Timestamp addTime;
    private double price;
    private String content;
    private int userId;

    public Product() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getPcId() {
        return pcId;
    }

    public void setPcId(int pcId) {
        this.pcId = pcId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + pname + '\'' +
                ", pcId=" + pcId +
                ", photo='" + photo + '\'' +
                ", addTime=" + addTime +
                ", price=" + price +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                '}';
    }
}
