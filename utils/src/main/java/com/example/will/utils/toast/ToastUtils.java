package com.example.will.utils.toast;

import android.content.Context;

public class ToastUtils {
    public static final int LENGTH_SHORT = 0;
    public static final int LENGTH_LONG = 1;


    public static final int SUCCESS = 1;
    public static final int WARNING = 2;
    public static final int ERROR = 3;
    public static final int INFO = 4;
    public static final int DEFAULT = 5;
    public static final int CONFUSING = 6;

    public static void showSuccessToast(Context context, String msg, int length) {
        TastyToast.makeText(context, msg, length, 1);
    }

    public static void showErrorToast(Context context, String msg, int length) {
        TastyToast.makeText(context, msg, length, 2);
    }

    public static void showInfoToast(Context context, String msg, int length) {
        TastyToast.makeText(context, msg, length, 3);
    }

    public static void showConfuseToast(Context context, String msg, int length) {
        TastyToast.makeText(context, msg, length, 5);
    }

    public static void showWarningToast(Context context, String msg, int length) {
        TastyToast.makeText(context, msg, length, 6);
    }

}
