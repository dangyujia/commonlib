package com.app.commonlib;

import android.util.Log;

public class LogUtils {
    private static final String TAG = BuildConfig.LIBRARY_PACKAGE_NAME;

    public static void e(String content) {
        if (BuildConfig.DEBUG)
            Log.e(TAG, content);
    }

    public static void i(String content) {
        if (BuildConfig.DEBUG)
            Log.i(TAG, content);
    }

    public static void v(String content) {
        if (BuildConfig.DEBUG)
            Log.v(TAG, content);
    }

    public static void e(String tag, String content) {
        if (BuildConfig.DEBUG)
            Log.e(tag, content);
    }

    public static void i(String tag, String content) {
        if (BuildConfig.DEBUG)
            Log.i(tag, content);
    }

    public static void v(String tag, String content) {
        if (BuildConfig.DEBUG)
            Log.v(tag, content);
    }

}
