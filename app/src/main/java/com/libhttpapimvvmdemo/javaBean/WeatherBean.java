package com.libhttpapimvvmdemo.javaBean;

import com.httpapi.BaseResultEntity;

import java.util.ArrayList;

// 所有的字段必须要和后台返回的一致，包括后台返回的字段是对象的，名字也必须要统一，要不GSON解析不了
public class WeatherBean extends BaseResultEntity<WeatherBean> {
    String city;// 字段必须要和后台返回的一致，要不GSON解析不了
    RealtimeBean realtime; // 字段必须要和后台返回的一致，要不GSON解析不了
    ArrayList<FutureBean> future;// 字段必须要和后台返回的一致，要不GSON解析不了

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public RealtimeBean getRealtime() {
        return realtime;
    }

    public void setRealtime(RealtimeBean realtime) {
        this.realtime = realtime;
    }

    public ArrayList<FutureBean> getFuture() {
        return future;
    }

    public void setFuture(ArrayList<FutureBean> future) {
        this.future = future;
    }
}
