package com.httpapi;

import android.databinding.BaseObservable;

import java.io.Serializable;
/**
 * 数据解析基类,可以根据自己的后台自行修改,正常情况下 把这个注释掉，按实际的修改就行， code，message，data 如果不一致，则要增加他们相应的get方法，否则RxSubscriber，QHttp
 * Api解析会报错找不到方法，当然您也可以自行修改QHTTPAPI 和RxSubscriber那解析部分就不用到了
 */
//public class BaseResultEntity<T> extends BaseObservable implements Serializable {
//    private int code; // 后台约定的code 这里后台返回200是成功
//    private String message; // 后台返回的提示信息
//    private boolean success; // 可有可无
//    private String state; // 可有可无
//    private T data; // 后台返回的数据data
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public boolean isSuccess() {
//        return success;
//    }
//
//    public void setSuccess(boolean success) {
//        this.success = success;
//    }
//
//    public String getState() {
//        return state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }
//
//    public T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }
//}

    // 测试用的 因为聚合函数返回的有点不一样
public class BaseResultEntity<T> extends BaseObservable implements Serializable {
    private int error_code; // 后台约定的code 这里后台返回200是成功
    private String reason; // 后台返回的提示信息
    private T result; // 后台返回的数据data

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    // 为了结合 QHttpApi解析，增加以下几个方法 返回 聚合函数中返回的其他数据
    // QHttpApi 使用的是 getData获取数据
    public T getData() {
        return result;
    }

    public int getCode() {
        if (error_code == 0)
            return 200; // http解析处用的是200来解析成功，可以自行修改 libhttpapi中的 HttpCode
        return error_code;
    }

    public String getMessage() {
        return reason;
    }
}
