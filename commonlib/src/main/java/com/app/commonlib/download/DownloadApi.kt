package com.app.commonlib.download

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

/**
 * @ClassName DownloadFile
 * @Author DYJ
 * @Date 2020/8/18 20:54
 * @Version 1.0
 * @Description 下载文件
 */

/**
 * 下载地址
 */
interface DownloadApi {
    /**
     * 下载文件
     */
    @Streaming //大文件时要加不然会OOM
    @GET
    fun downloadFile(@Url fileUrl: String): Call<ResponseBody>
}

/**
 * 接口返回
 */
interface DownloadListener {
    fun onStart()
    fun onProgress(currentLength: Int)
    fun onFinish(localPath: String)
    fun onFailure(errInfo: String)
}

class ApiHelper() {
    private var mRetrofit: Retrofit? = null
    private val mHttpClient: OkHttpClient


    fun buildRetrofit(baseUrl: String): ApiHelper {
        mRetrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mHttpClient)
                .build()
        return this
    }

    fun <T> createService(serviceClass: Class<T>): T {
        return mRetrofit!!.create(serviceClass)
    }

    companion object {
        private var mInstance: ApiHelper? = null
        val instance: ApiHelper?
            get() {
                if (mInstance == null) {
                    mInstance = ApiHelper()
                }
                return mInstance
            }
    }

    init {
        val builder = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
        mHttpClient = builder.build()
    }
}
