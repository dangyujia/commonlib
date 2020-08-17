package com.example.app.rainxutils;

import android.app.Application;


import org.xutils.x;

/**
 * @ClassName App
 * @Author DYJ
 * @Date 2020/6/29 15:20
 * @Version 1.0
 * @Description TODO
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);//xUtils初始化
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}
