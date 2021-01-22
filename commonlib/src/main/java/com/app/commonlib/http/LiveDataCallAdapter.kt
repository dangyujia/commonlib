package com.app.commonlib.http

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @ClassName LiveDataCallAdapter
 * @Author DYJ
 * @Date 2020/7/15 21:56
 * @Version 1.0
 * @Description  重写callAdapter的返回
 */
class LiveDataCallAdapter<T>(
    private val responseType: Type,
    var creator: (Int, String, Any?) -> Any
) : CallAdapter<T, LiveData<T>> {
    override fun adapt(call: Call<T>): LiveData<T> {
        return object : LiveData<T>() {
            private val started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {//确保执行一次
                    call.enqueue(object : Callback<T> {
                        override fun onFailure(call: Call<T>, t: Throwable) {
                            val value = creator(-1, t.message ?: "", null) as T
                            postValue(value)
                        }

                        override fun onResponse(call: Call<T>, response: Response<T>) {
                            postValue(response.body())
                        }
                    })
                }
            }
        }
    }

    override fun responseType(): Type = responseType
}