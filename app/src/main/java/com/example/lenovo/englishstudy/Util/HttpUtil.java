package com.example.lenovo.englishstudy.Util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

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
}
