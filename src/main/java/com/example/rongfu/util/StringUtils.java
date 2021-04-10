package com.example.rongfu.util;

public class StringUtils {
    public static boolean isPhone(String phone) {
        return phone.length() != 0 && phone.matches("^[1][3,4,5,7,8][0-9]{9}$");
    }

    public static boolean isEmail(String email) {
        return email.length() != 0 && email.matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
    }
}
