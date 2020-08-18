package com.app.commonlib.http

import com.app.commonlib.Commonlib
import com.app.rain.http.retrofitlivedata.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @ClassName RetrofitClient
 * @Author DYJ
 * @Date 2020/7/15 22:07
 * @Version 1.0
 * @Description 构建retrofit
 */
inline fun <reified T> create(
        baseUrl: String,
        saveCookie: Boolean,
        noinline creator: (Int, String, Any?) -> Any
): T {

    return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(LiveDataCallAdapterFactory(creator))//liveData构造器
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient())
            .build()
            .create(T::class.java)


}

/*构建okHttp*/
fun okHttpClient() = OkHttpClient.Builder()
        .connectTimeout(Commonlib.httpTime(), TimeUnit.SECONDS)
        .readTimeout(Commonlib.httpTime(), TimeUnit.SECONDS)
        .addNetworkInterceptor(loggingInterceptor())
        .build()