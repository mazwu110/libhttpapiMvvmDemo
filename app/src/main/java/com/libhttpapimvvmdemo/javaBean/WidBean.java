package com.libhttpapimvvmdemo.javaBean;

import android.databinding.BaseObservable;

import java.io.Serializable;

public class WidBean extends BaseObservable implements Serializable {
    String day;
    String night;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }
}
