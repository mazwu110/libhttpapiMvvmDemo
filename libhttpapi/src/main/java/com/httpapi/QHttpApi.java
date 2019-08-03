package com.httpapi;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.httpapi.apiservice.HttpApiService;
import com.httpapi.apiservice.HttpCode;
import com.httpapi.apiservice.OnHttpApiListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * author mzw  2019-06-29
 * MVVM中的M，使用了泛型的M，就是所有的VM可以统一来使用它掉起后台数据
 * 在这里只需要一个ApiService，retrofit2一般是使用多个ApiService，但这里封装成了一个，
 * 您只需要传地址和参数进来就行
 * 全是泛型解析，ApiService也只需要一个 棒棒棒
 * 特别说明，只能解析标准的JSON对象和JSON数组
 */
public class QHttpApi {
    static volatile Gson gson = new Gson();

    // get请求
    public static void doGet(String url, Map<String, Object> params, final Class<?> clazz,
                             final int what, final OnHttpApiListener listener) {
        HttpHelper.api().create(HttpApiService.class)
                .doGet(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RxSubscriber<BaseResultEntity>() {
                    @Override
                    public void onSuccess(BaseResultEntity data) {
                        parseDataApi(what, data, clazz, listener);
                    }

                    @Override
                    public void onFailure(String msg, int code) {
                        if (listener != null)
                            listener.onFailure(what, msg, code);
                    }
                });
    }

    // post请求 key-value格式
    public static void doPost(String url, Map<String, Object> params, final Class<?> clazz,
                              final int what, final OnHttpApiListener listener) {
        HttpHelper.api().create(HttpApiService.class)
                .doPost(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RxSubscriber<BaseResultEntity>() {
                    @Override
                    public void onSuccess(BaseResultEntity data) {
                        parseDataApi(what, data, clazz, listener);
                    }

                    @Override
                    public void onFailure(String msg, int code) {
                        listener.onFailure(what, msg, code);
                    }
                });
    }

    // post请求表单格式
    public static void doFormPost(String url, Map<String, Object> params, final Class<?> clazz,
                                  final int what, final OnHttpApiListener listener) {
        HttpHelper.api().create(HttpApiService.class)
                .doFormPost(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RxSubscriber<BaseResultEntity>() {
                    @Override
                    public void onSuccess(BaseResultEntity data) {
                        parseDataApi(what, data, clazz, listener);
                    }

                    @Override
                    public void onFailure(String msg, int code) {
                        listener.onFailure(what, msg, code);
                    }
                });
    }

    // JSON 参数请求的post
    public static void doJsonPost(String url, Map<String, Object> params, final Class<?> clazz,
                                  final int what, final OnHttpApiListener listener) {
        String json = buildJsonParamsRequest(params).toString();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        HttpHelper.api().create(HttpApiService.class)
                .doJsonPost(url, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RxSubscriber<BaseResultEntity>() {
                    @Override
                    public void onSuccess(BaseResultEntity data) {
                        parseDataApi(what, data, clazz, listener);
                    }

                    @Override
                    public void onFailure(String msg, int code) {
                        listener.onFailure(what, msg, code);
                    }
                });
    }

    // 统一解析数据
    private static void parseDataApi(int what, BaseResultEntity data, Class<?> clazz, OnHttpApiListener listener) {
        String str = data.getData().toString();
        if (TextUtils.isEmpty(str)) {
            if (listener != null)
                listener.onSuccess(what, data.getData()); // 后台返回空交给前端自己判读
            return;
        }

        // gson泛型解析不了 /，暂时未找到原因
        str = str.replaceAll("/", "--");
        Object obj = gson.fromJson(str, (Type) clazz);
        if (listener != null)
            listener.onSuccess(what, obj);
    }

    // 直接用OKHTTP3返回普通的字符串，即不做泛型解析，后台返回什么交给开发者自己解析,避免后台返回特殊数据，比如XML等
    public static void doStrGet(String url, Map<String, Object> params,
                                final int what, final OnHttpApiListener listener) {
        Request.Builder builder = new Request.Builder();
        String newUrl = appendParams(url, params);
        final Request request = builder.url(newUrl)
                .get()
                .build();
        final Call call = HttpHelper.api().getOkHttpClient().newCall(request);
        // 异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailure(what, "请求失败", HttpCode.ERROR_NETWORK);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    listener.onSuccess(what, response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private static String appendParams(String url, Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url + "?");
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private static JSONObject buildJsonParamsRequest(Map<String, Object> params) {
        JSONObject obj = new JSONObject();
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                try {
                    obj.put(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return obj;
    }
}
