package com.httpapi.interceptor;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Set;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author mzw 2019-07-24 http统一请求头
 */
public class MyInterceptor implements Interceptor {
    private Map<String, Object> headers;

    public MyInterceptor(Map<String, Object> headers) {
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = null;
        //同步 避免溢出
        synchronized (this) {
            Request.Builder builder = chain.request().newBuilder();
            if (headers != null && headers.size() > 0) {
                Set<String> keys = headers.keySet();
                for (String key : keys) {
                    builder.addHeader(key, headers.get(key).toString()).build();
                }
                try {
                    response = chain.proceed(builder.build());
                } catch (SocketTimeoutException e) {
                    e.getLocalizedMessage();
                }
            } else {
                response = chain.proceed(builder.build());
            }
        }

        return response;
    }
}
