package com.app.commonlib

import android.content.Context
import android.os.Handler
import android.os.Looper

/**
 * 初始化全工程handler
 */
val MAIN_HANDLER = Handler(Looper.getMainLooper())

/**
 * @delay 时间
 */
fun postDelay(block: () -> Unit, delay: Long) {
    MAIN_HANDLER.postDelayed({ block() }, delay)
}

fun post(block: () -> Unit) {
    postDelay(block, 0)
}

/**
 * 获取屏幕宽度
 */
val Context.screenWidth
    get() = resources.displayMetrics.widthPixels

/**
 * 获取屏幕高度
 */
val Context.screenHeight
    get() = resources.displayMetrics.heightPixels
