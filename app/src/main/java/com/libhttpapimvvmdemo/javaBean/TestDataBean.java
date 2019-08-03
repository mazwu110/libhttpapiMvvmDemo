package com.libhttpapimvvmdemo.javaBean;

import android.databinding.BaseObservable;

import java.io.Serializable;

// 测试用的单个数据绑定
public class TestDataBean extends BaseObservable implements Serializable {
    String usreName;

    public String getUsreName() {
        return usreName;
    }

    public void setUsreName(String usreName) {
        this.usreName = usreName;
    }
}
