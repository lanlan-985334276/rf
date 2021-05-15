package com.example.rongfu.entity;

public class Summary {
    private int todayPurchase;
    private int purchase;
    private int todayDelivery;
    private int delivery;
    private int todayReturnGoods;
    private int returnGoods;

    public Summary() {
    }

    public int getTodayPurchase() {
        return todayPurchase;
    }

    public void setTodayPurchase(int todayPurchase) {
        this.todayPurchase = todayPurchase;
    }

    public int getPurchase() {
        return purchase;
    }

    public void setPurchase(int purchase) {
        this.purchase = purchase;
    }

    public int getTodayDelivery() {
        return todayDelivery;
    }

    public void setTodayDelivery(int todayDelivery) {
        this.todayDelivery = todayDelivery;
    }

    public int getDelivery() {
        return delivery;
    }

    public void setDelivery(int delivery) {
        this.delivery = delivery;
    }

    public int getTodayReturnGoods() {
        return todayReturnGoods;
    }

    public void setTodayReturnGoods(int todayReturnGoods) {
        this.todayReturnGoods = todayReturnGoods;
    }

    public int getReturnGoods() {
        return returnGoods;
    }

    public void setReturnGoods(int returnGoods) {
        this.returnGoods = returnGoods;
    }

    @Override
    public String toString() {
        return "Summary{" +
                "todayPurchase=" + todayPurchase +
                ", purchase=" + purchase +
                ", todayDelivery=" + todayDelivery +
                ", delivery=" + delivery +
                ", todayReturnGoods=" + todayReturnGoods +
                ", returnGoods=" + returnGoods +
                '}';
    }
}
