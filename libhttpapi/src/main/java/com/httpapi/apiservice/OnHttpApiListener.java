package com.httpapi.apiservice;

/**
 * author mzw 2019-07-24
 */
public interface OnHttpApiListener {
    // data 只包含后台返回的data数据哦，其他比如code处理放在了RxSubscriber
    void onSuccess(int what, Object data);
    void onFailure(int what, String msg, int code);
}
