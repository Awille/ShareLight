package com.example.will.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtils {
    private static SimpleDateFormat format1= new SimpleDateFormat("yyyy-MM-dd");

    public static String int2StringFormat1(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return format1.format(calendar.getTime());
    }

    /**
     * 从秒转换成 mm:ss
     * @param s  秒
     * @return  mm:ss 分:秒
     */
    public static String fromS2MS(int s) {
        int m = s / 60;
        int s2 = s % 60;
        String minute = (m >= 10 ? String.valueOf(m) : ("0" + String.valueOf(m)));
        String second = (s2 >= 10 ? String.valueOf(s2) : ("0" + String.valueOf(s2)));
        return (minute + ":" + second);
    }
}
