package com.example.rongfu.util;

import com.example.rongfu.entity.Staff;
import com.example.rongfu.entity.User;
import com.google.gson.Gson;

public class JsonUtils {
    public static User json2User(String json) {
        return new Gson().fromJson(json, User.class);
    }
    public static Staff json2Staff(String json) {
        return new Gson().fromJson(json, Staff.class);
    }
}
