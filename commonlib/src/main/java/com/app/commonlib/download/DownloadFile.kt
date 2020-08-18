package com.app.commonlib.download

import android.text.TextUtils
import android.util.Log
import com.app.commonlib.Commonlib.BASE_URL
import com.app.commonlib.Commonlib.SAVE_IMAGE_PATH
import com.app.commonlib.Commonlib.SAVE_MEDIA_PATH
import com.app.commonlib.utils.FileUtils

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*

/**
 * @ClassName: DownloadUtil
 * @Description: 下载文件工具类
 * @Author: Rain
 * @Version: 1.0
 */
class DownloadFile {
    //视频下载相关
    private var mApi: DownloadApi? = null
    private var mCall: Call<ResponseBody>? = null
    private var mFile: File? = null
    private var mThread: Thread? = null
    private var mVideoPath = "" //下载到本地的视频路径

    /**
     *
     * @param url
     * @param downloadListener
     * @param MIMEType 0:图片 1：视频
     */
    fun downloadFile(
            url: String,
            downloadListener: DownloadListener,
            MIMEType: Int
    ) {
        //通过Url得到保存到本地的文件名
        var name = url
        val dir = if (MIMEType == 1) SAVE_MEDIA_PATH else SAVE_IMAGE_PATH
        if (FileUtils.createOrExistsDir(dir)) {
            val i = name.lastIndexOf('/') //一定是找最后一个'/'出现的位置
            if (i != -1) {
                name = name.substring(i)
                mVideoPath = dir +
                        name
            }
        }
        if (TextUtils.isEmpty(mVideoPath)) {
            Log.e(TAG, "downloadVideo: 存储路径为空了")
            return
        }
        //建立一个文件
        mFile = File(mVideoPath)
        if (!FileUtils.isFileExists(mFile) && FileUtils.createOrExistsFile(mFile)) {
            if (mApi == null) {
                Log.e(TAG, "downloadVideo: 下载接口为空了")
                return
            }
            mCall = mApi?.downloadFile(url)
            mCall!!.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(
                        call: Call<ResponseBody?>,
                        response: Response<ResponseBody?>
                ) {
                    //下载文件放在子线程
                    mThread = object : Thread() {
                        override fun run() {
                            super.run()
                            //保存到本地
                            writeFile2Disk(response, mFile!!, downloadListener)
                        }
                    }
                    mThread?.start()
                }

                override fun onFailure(
                        call: Call<ResponseBody?>,
                        t: Throwable
                ) {
                    downloadListener.onFailure("网络错误！")
                }
            })
        } else {
            downloadListener.onFinish(mVideoPath!!)
        }
    }

    private fun writeFile2Disk(
            response: Response<ResponseBody?>,
            file: File,
            downloadListener: DownloadListener
    ) {
        downloadListener.onStart()
        var currentLength: Long = 0
        var os: OutputStream? = null
        if (response.body() == null) {
            downloadListener.onFailure("资源错误！")
            return
        }
        val iss = response.body()!!.byteStream()
        val totalLength = response.body()!!.contentLength()
        try {
            os = FileOutputStream(file)
            var len: Int
            val buff = ByteArray(1024)
            while (iss.read(buff).also { len = it } != -1) {
                os.write(buff, 0, len)
                currentLength += len.toLong()
                Log.e(TAG, "当前进度: $currentLength")
                downloadListener.onProgress((100 * currentLength / totalLength).toInt())
                if ((100 * currentLength / totalLength).toInt() == 100) {
                    downloadListener.onFinish(mVideoPath)
                }
            }
        } catch (e: FileNotFoundException) {
            downloadListener.onFailure("未找到文件！")
            e.printStackTrace()
        } catch (e: IOException) {
            downloadListener.onFailure("IO错误！")
            e.printStackTrace()
        } finally {
            if (os != null) {
                try {
                    os.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (iss != null) {
                try {
                    iss.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {
        private const val TAG = "================"

    }

    init {
        if (mApi == null) {
            mApi = ApiHelper.instance?.buildRetrofit(BASE_URL)?.createService(DownloadApi::class.java)
        }
    }
}