package com.example.rongfu.util;

import com.example.rongfu.entity.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {
    public static User json2User(String json) {
        return new GsonBuilder()
                .registerTypeAdapter(int.class, new IntTypeAdapter())
                .registerTypeAdapter(Integer.class, new IntTypeAdapter()).create().fromJson(json, User.class);
    }

    public static Staff json2Staff(String json) {
        return new GsonBuilder()
                .registerTypeAdapter(int.class, new IntTypeAdapter())
                .registerTypeAdapter(Integer.class, new IntTypeAdapter()).create().fromJson(json, Staff.class);
    }

    public static ProductCategory json2Category(String json) {
        return new GsonBuilder()
                .registerTypeAdapter(int.class, new IntTypeAdapter())
                .registerTypeAdapter(Integer.class, new IntTypeAdapter()).create().fromJson(json, ProductCategory.class);
    }

    public static Product json2Product(String json) {
        return new GsonBuilder()
                .registerTypeAdapter(int.class, new IntTypeAdapter())
                .registerTypeAdapter(Integer.class, new IntTypeAdapter()).create().fromJson(json, Product.class);
    }
    public static Customer json2Customer(String json) {
        return new GsonBuilder()
                .registerTypeAdapter(int.class, new IntTypeAdapter())
                .registerTypeAdapter(Integer.class, new IntTypeAdapter()).create().fromJson(json, Customer.class);
    }
    public static Admin json2Admin(String json) {
        return new GsonBuilder()
                .registerTypeAdapter(int.class, new IntTypeAdapter())
                .registerTypeAdapter(Integer.class, new IntTypeAdapter()).create().fromJson(json, Admin.class);
    }
    public static Plan json2Plan(String json) {
        return new GsonBuilder()
                .registerTypeAdapter(int.class, new IntTypeAdapter())
                .registerTypeAdapter(Integer.class, new IntTypeAdapter()).create().fromJson(json, Plan.class);
    }
    public static SignIn json2SignIn(String json) {
        return new GsonBuilder()
                .registerTypeAdapter(int.class, new IntTypeAdapter())
                .registerTypeAdapter(Integer.class, new IntTypeAdapter()).create().fromJson(json, SignIn.class);
    }
    public static Purchase json2Purchase(String json) {
        return new GsonBuilder()
                .registerTypeAdapter(int.class, new IntTypeAdapter())
                .registerTypeAdapter(Integer.class, new IntTypeAdapter()).create().fromJson(json, Purchase.class);
    }
    public static Delivery json2Delivery(String json) {
        return new GsonBuilder()
                .registerTypeAdapter(int.class, new IntTypeAdapter())
                .registerTypeAdapter(Integer.class, new IntTypeAdapter()).create().fromJson(json, Delivery.class);
    }
    public static ReturnGoods json2ReturnGoods(String json) {
        return new GsonBuilder()
                .registerTypeAdapter(int.class, new IntTypeAdapter())
                .registerTypeAdapter(Integer.class, new IntTypeAdapter()).create().fromJson(json, ReturnGoods.class);
    }
    public static LogEntity json2LogEntity(String json) {
        return new GsonBuilder()
                .registerTypeAdapter(int.class, new IntTypeAdapter())
                .registerTypeAdapter(Integer.class, new IntTypeAdapter()).create().fromJson(json, LogEntity.class);
    }
    public static Step json2Step(String json) {
        return new GsonBuilder()
                .registerTypeAdapter(int.class, new IntTypeAdapter())
                .registerTypeAdapter(Integer.class, new IntTypeAdapter()).create().fromJson(json, Step.class);
    }
}
