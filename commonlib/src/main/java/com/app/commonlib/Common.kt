package com.app.commonlib

import android.content.Context
import android.os.Environment
import java.io.File
import kotlin.properties.Delegates

/**
 * @ClassName Commonlib
 * @Author DYJ
 * @Date 2020/8/18 19:53
 * @Version 1.0
 * @Description 全局context
 */
object Common {

    private lateinit var context: Context

    private lateinit var HTTP_LOG_TAG: String

    private var HTTP_TIME_OUT = 5L

    private var filePath = "Rain"

    private val SAVE_FILE_PATH = Environment.getExternalStorageDirectory().absolutePath + File.separator + filePath

    val SAVE_MEDIA_PATH = SAVE_FILE_PATH + File.separator + "media" //视频地址

    val SAVE_IMAGE_PATH = SAVE_FILE_PATH + File.separator + "image" //图片地址

    var BASE_URL = ""

    var ERROR = 0

    var PLACEHOLDER = 0

    fun context(): Context = context

    fun setContext(context: Context) {
        this.context = context
    }

    fun httpLog(): String = HTTP_LOG_TAG

    fun setHttpLog(log: String) {
        HTTP_LOG_TAG = log
    }

    fun httpTime(): Long = HTTP_TIME_OUT

    fun setHttpTime(time: Long) {
        HTTP_TIME_OUT = time
    }

    fun setFilePath(path: String) {
        filePath = path
    }

}