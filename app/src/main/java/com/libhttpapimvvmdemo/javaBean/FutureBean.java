package com.libhttpapimvvmdemo.javaBean;

import android.databinding.BaseObservable;
import java.io.Serializable;

public class FutureBean extends BaseObservable implements Serializable {
    String date;
    String temperature;
    String weather;
    WidBean wid;
    String direct;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public WidBean getWid() {
        return wid;
    }

    public void setWid(WidBean wid) {
        this.wid = wid;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }
}
