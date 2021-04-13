package com.example.rongfu.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static String getTodayDate() {
        return new SimpleDateFormat("yyyy-mm-dd").format(new Date().getTime());
    }

    public static long getToadyDateTimeMillis() throws ParseException {
        return new SimpleDateFormat("yyyy-mm-dd").parse(getTodayDate()).getTime();
    }

    public static long getDateTimeMillis(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTimeInMillis();
    }

    public static long getTomorrowDateTimeMills() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        return calendar.getTimeInMillis();
    }

    public static Timestamp getDate(String time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date date = format.parse(time);
        return new Timestamp(date.getTime());

    }
}
