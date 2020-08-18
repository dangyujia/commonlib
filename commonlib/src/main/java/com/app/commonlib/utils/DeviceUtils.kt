package com.app.commonlib.utils

import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import java.net.NetworkInterface
import java.util.*

/**
 * @ClassName DeviceUtils
 * @Author DYJ
 * @Date 2020/8/18 20:02
 * @Version 1.0
 * @Description 获取手机设备号
 */
object DeviceUtils {

    /**
     * 获取设备系统版本号
     */
    fun getSDKVersion(): Int = Build.VERSION.SDK_INT

    /**
     * 获取设备AndroidID
     */
    fun getAndroidID(): String = Settings.Secure.ANDROID_ID

    /**
     * 获取设备厂商
     */
    fun getManufacturer(): String = Build.MANUFACTURER

    /**
     * 获取设备型号
     */
    fun getModel(): String = Build.MODEL

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     */
    @RequiresApi(Build.VERSION_CODES.GINGERBREAD)
    fun getMacAddressByNetworkInterface(): String {
        try {
            val nis: List<NetworkInterface> = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (ni in nis) {
                if (!ni.name.equals("wlan0", ignoreCase = true)) continue
                val macBytes = ni.hardwareAddress
                if (macBytes != null && macBytes.isNotEmpty()) {
                    val res1 = StringBuilder()
                    for (b in macBytes) {
                        res1.append(String.format("%02x:", b))
                    }
                    return res1.deleteCharAt(res1.length - 1).toString()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "02:00:00:00:00:00"
    }
}