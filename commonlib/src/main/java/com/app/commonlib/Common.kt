package com.app.commonlib

import android.content.Context
import android.os.Environment
import java.io.File
import kotlin.properties.Delegates

/**
 * @ClassName Common
 * @Author DYJ
 * @Date 2020/8/18 19:53
 * @Version 1.0
 * @Description 全局context
 */
object Common {

    /**
     * 上下文
     */
    private lateinit var context: Context

    /**
     * 日志打印前缀
     */
    private lateinit var HTTP_LOG_TAG: String

    /**
     * 网络超时时间 单位秒
     */
    private var HTTP_TIME_OUT = 15L

    /**
     * 文件名称
     */
    private var filePath = "Rain"

    /**
     * 保存文件路径
     */
    private val SAVE_FILE_PATH = Environment.getExternalStorageDirectory().absolutePath + File.separator + filePath

    /**
     * 视频地址
     */
    val SAVE_MEDIA_PATH = SAVE_FILE_PATH + File.separator + "media" //视频地址

    /**
     * 图片地址
     */
    val SAVE_IMAGE_PATH = SAVE_FILE_PATH + File.separator + "image" //图片地址

    /**
     * Host 地址
     */
    var BASE_URL = ""

    /**
     *  图片错误加载地址
     */
    var ERROR = 0

    /**
     * 图片加载的占位图
     */
    var PLACEHOLDER = 0

    fun context(): Context = context

    fun setContext(context: Context) {
        this.context = context
    }

    fun httpLog(): String = HTTP_LOG_TAG ?: ""

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