package com.example.rongfu.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static Calendar getTodayDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c;
    }

    public static Calendar getTomorrowDate() {
        Calendar calendar = getTodayDate();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        return calendar;
    }


    public static long getToadyDateTimeMillis() {
        return getTodayDate().getTimeInMillis();
    }

    public static long getDateTimeMillis(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTimeInMillis();
    }

    public static long getTomorrowDateTimeMills() {
        return getTomorrowDate().getTimeInMillis();
    }

    public static Timestamp getDate(String time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date date = format.parse(time);
        return new Timestamp(date.getTime());
    }
}
