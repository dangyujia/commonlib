package com.app.commonlib.http

import androidx.lifecycle.LiveData
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @ClassName LiveDataCallAdapterFactory
 * @Author DYJ
 * @Date 2020/7/15 21:53
 * @Version 1.0
 * @Description 重写 adapter 工厂 弃用  弃用  整合到 CallAdapterFactory
 */
class LiveDataCallAdapterFactory(var creator: (Int, String, Any?) -> Any) : CallAdapter.Factory() {
    override fun get(
            returnType: Type,
            annotations: Array<Annotation>,
            retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData::class.java) return null
        //获取第一个泛型类型
        val observableType =
                getParameterUpperBound(0, returnType as ParameterizedType)
        val rawType = getRawType(observableType)
        if (rawType != creator(0, "", null).javaClass) {
            throw IllegalArgumentException("type must be ApiResponse")
        }
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }
        return LiveDataCallAdapter<Any>(observableType, creator)
    }
}