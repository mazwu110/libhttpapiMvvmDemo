package com.httpapi;

import com.httpapi.apiservice.HttpCode;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.HttpException;

public abstract class RxSubscriber<T> extends DisposableSubscriber<T> {
    public RxSubscriber() {
        super();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        String message;
        int code = -1;
        if (e instanceof UnknownHostException) {
            message = "没有网络";
        } else if (e instanceof HttpException) {
            message = "网络错误";
        } else if (e instanceof SocketTimeoutException) {
            message = "网络连接超时";
        } else if (e instanceof ConnectException) {
            message = "连接失败";
        } else{
            message = "未知错误";
        }

        onFailure(message, code);
    }

    @Override
    public void onNext(T t) {
        if (t == null) {
            onFailure("请求出错，请稍后再试", -1);
            return;
        }
        // 错误统一处理
        BaseResultEntity base = (BaseResultEntity)t;
        int code = base.getCode();
        if (code == HttpCode.SUCCESS) {
            // 服务端返回成功
            onSuccess(t);
        } else {
            String errorMsg = base.getMessage();

            // 公共错误逻辑处理
            switch (code) {
                case HttpCode.ERROR_LOGIN_INVALID:
                    break;
                default:
                    break;
            }

            // 服务端返回错误
            onFailure(errorMsg, code);
        }
    }

    /**
     * success
     *
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * failure
     *
     * @param msg
     */
    public abstract void onFailure(String msg, int code);
}
