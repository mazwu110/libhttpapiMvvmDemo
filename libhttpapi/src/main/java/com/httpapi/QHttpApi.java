package com.httpapi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.httpapi.apiservice.HttpApiService;
import com.httpapi.apiservice.HttpCode;
import com.httpapi.apiservice.OnHttpApiListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.http.HeaderMap;

/**
 * author mzw  2019-06-29
 * MVVM中的M，使用了泛型的M，就是所有的VM可以统一来使用它掉起后台数据
 * 在这里只需要一个ApiService，retrofit2一般是使用多个ApiService，但这里封装成了一个，
 * 您只需要传地址和参数进来就行
 * 全是泛型解析，ApiService也只需要一个 棒棒棒
 * 特别说明，只能解析标准的JSON对象和JSON数组,data空的会返回给前端自己解析的哦
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

    private static <T> void parseDataApi(int what, BaseResultEntity data, Class<T> clazz, OnHttpApiListener listener) {
        if (data.getData() == null || data.getData().equals("") || data.getData().equals("null")) {
            listener.onSuccess(what, data.getData()); //返回空由前端自己判断是成功还是失败，这里默认返回在成功接口里
            return;
        }

        // 必须先转JSON字符串，否则泛型转换会报错
        String json = gson.toJson(data.getData());
        // 非JSON格式的
        if ((!json.contains("{") && !json.contains("}"))
                && (!json.contains("[") && !json.contains("]"))) {
            listener.onSuccess(what, data.getData()); // 后台返回空或者直接在data中返回提示语的交给前端自己判读
            return;
        }

        // 纯JSON数组的,比如data:[],调用处传递的参数格式必须clazz是MyClassName[].class,否则不能解析
        // 接收处直接 List<MyClassName> list = (List<MyClassName>)data; 即可接收到后台传回的Data数据
        if (data.getData() instanceof ArrayList) {
            T[] backData = gson.fromJson(json, (Type)clazz);
            listener.onSuccess(what, Arrays.asList(backData));
            // 不能简写成下面这句，要不，接收数据处直接用List<MyClassName> list = (List<MyClassName>)data的话会报错，我也不知道是什么问题
            //反正分开像上面这种写法就可以，下面这个就不行，看了老外写的学来的
            //listener.onSuccess(what, Arrays.asList(gson.fromJson(json, (Type)clazz)));
            return;
        }

        // 非纯JSON数组的,比如data:{"list":[], "userName":"123"},调用处传递的参数格式必须clazz是MyClassName.class,否则不能解析,
        // 接收处直接 MyClassName bean = (MyClassName)data; 即可; 即可接收到后台传回的Data数据
        BaseResultEntity result = gson.fromJson(json, (Type) clazz);
        listener.onSuccess(what, result);
    }

    // 直接用OKHTTP3返回普通的字符串，即不做泛型解析，后台返回什么交给开发者自己解析,避免后台返回特殊数据，比如XML等
    // post请求可以自己模仿写即可
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

    // 直接用OKHTTP3返回普通的字符串，即不做泛型解析
    public static void doStrPost(String url, Map<String, Object> params,
                                 final int what, final OnHttpApiListener listener) {
        String json = buildJsonParamsRequest(params).toString();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request.Builder builder = new Request.Builder();
        String newUrl = appendParams(url, params);
        final Request request = builder.url(newUrl)
                .post(body)
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

    // 单文件上传
    public static void uploadFile(String url, @HeaderMap Map<String, String> headers, File files, String key,
                                  final Class<?> clazz, final int what, final OnHttpApiListener listener) {
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), files);
        MultipartBody.Part part = MultipartBody.Part.createFormData(key, files.getName(), fileBody);

        HttpHelper.api().create(HttpApiService.class)
                .uploadFile(url, part)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RxSubscriber<BaseResultEntity>() {
                    @Override
                    public void onSuccess(BaseResultEntity data) {
                        if (listener != null)
                            parseDataApi(what, data, clazz, listener);
                    }

                    @Override
                    public void onFailure(String msg, int code) {
                        if (listener != null)
                            listener.onFailure(what, msg, code);
                    }
                });
    }

    // 多文件上传
    public static void uploadFiles(String url, @HeaderMap Map<String, String> headers, List<File> files, String key,
                                   final Class<?> clazz, final int what, final OnHttpApiListener listener) {
        List<MultipartBody.Part> partList = new ArrayList();

        for (int i = 0; i < files.size(); i++) {
            RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), files.get(i));
            MultipartBody.Part part = MultipartBody.Part.createFormData(key, files.get(i).getName(), fileBody);
            partList.add(part);
        }

        HttpHelper.api().create(HttpApiService.class)
                .uploadFiles(url, partList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RxSubscriber<BaseResultEntity>() {
                    @Override
                    public void onSuccess(BaseResultEntity data) {
                        if (listener != null)
                            parseDataApi(what, data, clazz, listener);
                    }

                    @Override
                    public void onFailure(String msg, int code) {
                        if (listener != null)
                            listener.onFailure(what, msg, code);
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
