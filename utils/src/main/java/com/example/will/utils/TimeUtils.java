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
}
