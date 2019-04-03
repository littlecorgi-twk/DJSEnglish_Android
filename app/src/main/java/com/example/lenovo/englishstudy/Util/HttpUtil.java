package com.example.lenovo.englishstudy.Util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author littlecorgi
 * @Date 2019-03-10 19:29
 * @email a1203991686@126.com
 */
public class HttpUtil {

    static OkHttpClient okHttpClient = new OkHttpClient();

    public static void sendHttpRequest(String address, okhttp3.Callback callback) {
        Request request = new Request.Builder().url(address).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void sendHttpRequest(String address, RequestBody requestBody, okhttp3.Callback callback) {
        Request request = new Request.Builder().url(address)
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .post(requestBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}
