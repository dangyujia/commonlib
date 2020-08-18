package com.app.commonlib.utils

import android.util.Log
import com.app.commonlib.BuildConfig

/**
 * @ClassName DeviceUtils
 * @Author DYJ
 * @Date 2020/8/18 20:02
 * @Version 1.0
 * @Description 日志
 */
object LogUtils {

    private const val TAG = BuildConfig.LIBRARY_PACKAGE_NAME

    fun e(content: String) {
        if (BuildConfig.DEBUG) Log.e(TAG, content)
    }

    fun i(content: String) {
        if (BuildConfig.DEBUG) Log.i(TAG, content)
    }

    fun v(content: String) {
        if (BuildConfig.DEBUG) Log.v(TAG, content)
    }

    fun e(tag: String, content: String) {
        if (BuildConfig.DEBUG) Log.e(tag, content)
    }

    fun i(tag: String, content: String) {
        if (BuildConfig.DEBUG) Log.i(tag, content)
    }

    fun v(tag: String, content: String) {
        if (BuildConfig.DEBUG) Log.v(tag, content)
    }
}