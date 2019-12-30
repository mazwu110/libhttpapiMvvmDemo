package com.qlib.base;

import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;

import com.qlib.qutils.GlideUtils;
import com.qlib.qutils.ToastUtils;

public class QApp extends MultiDexApplication {
    public static GlideUtils mImageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.init(this);
        mImageLoader = GlideUtils.getInstance(getApplicationContext());

        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }
}
