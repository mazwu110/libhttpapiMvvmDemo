package com.httpapi.apiservice;

import com.httpapi.BaseResultEntity;

import java.util.List;
import java.util.Map;
import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 *  author mzw 2019-07-24
 * @param
 */
public interface HttpApiService {
    // get请求
    @GET
    Flowable<BaseResultEntity> doGet(@Url String url, @QueryMap Map<String, Object> params);

    //key-value提交
    @POST
    Flowable<BaseResultEntity> doPost(@Url String url, @QueryMap Map<String, Object> params);

    //表单提交
    @FormUrlEncoded
    @POST
    Flowable<BaseResultEntity> doFormPost(@Url String url, @QueryMap Map<String, Object> params);

    // json 格式的参数
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST
    Flowable<BaseResultEntity> doJsonPost(@Url String url, @Body RequestBody body);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST
    Flowable<BaseResultEntity> doJsonPost(@Url String url, @HeaderMap Map<String, String> headers, @Body RequestBody body);

    /**
     * 单文件上传
     */
    @Multipart
    @POST
    Flowable<BaseResultEntity> uploadFile(@Url String url, @Part MultipartBody.Part part);

    // 多文件上传
    @Multipart
    @POST
    Flowable<BaseResultEntity> uploadFiles(@Url String url, @Part List< MultipartBody.Part> partList);

    /**
     * 下载
     * */
    @Streaming
    @GET
    Flowable<BaseResultEntity> downloadFile(@Url String url);
}
