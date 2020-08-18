package com.app.commonlib.http

import android.util.Log
import com.app.commonlib.BuildConfig
import com.app.commonlib.Commonlib
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

/**
 * @ClassName OkHttpInterceptor
 * @Author DYJ
 * @Date 2020/7/12 15:26
 * @Version 1.0
 * @Description 拦截器
 */


/*log 日志拦截 */
fun loggingInterceptor() =
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d(Commonlib.httpLog(), message)
            }
        }).apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

/*cookie 拦截 */
fun cookieInterceptor() = object : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        //添加头部
//            requestBuilder.addHeader("Cookie", it)
        val response = chain.proceed(requestBuilder.build())
        val cookieList = response.headers("Set-Cookie")
        //设置cokies
        return response
    }
}
