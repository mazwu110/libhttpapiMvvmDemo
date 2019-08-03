package com.libhttpapimvvmdemo.javaBean;

import android.databinding.BaseObservable;

import com.httpapi.BaseResultEntity;

import java.io.Serializable;

// 基础数据解析类
public class RealtimeBean extends BaseObservable implements Serializable {
    String temperature;
    String humidity;
    String info;
    String wid;
    String direct;
    String power;
    String aqi;
}
