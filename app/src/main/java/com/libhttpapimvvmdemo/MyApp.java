package com.libhttpapimvvmdemo;

import com.httpapi.HttpHelper;
import com.qlib.base.QApp;

public class MyApp extends QApp {
    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化 QHttpApi 使用的 HttpHelper，包括地址，拦截器等
        new HttpHelper.Builder(this)
                .initOkHttp()
                .createRetrofit(Constants.IP)
                .build();
    }
}
