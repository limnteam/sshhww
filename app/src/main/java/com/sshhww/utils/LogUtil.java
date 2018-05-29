package com.sshhww.utils;

import android.util.Log;

/**
 * author： xiongdejin
 * date: 2018/4/19
 * describe:
 */
public class LogUtil {
    private static String TAG = "demo";

    public static void d(String msg) {
        if (msg == null) return;
        Log.d(TAG, msg);
    }

    public static void i(String msg){
        if (msg == null) return;
        Log.i(TAG, msg);
    }

    public static void w(String msg) {
        if (msg == null) return;
        Log.w(TAG, msg);
    }

    public static void e(String msg) {
        if (msg == null) return;
        Log.e(TAG, msg);
    }

}
