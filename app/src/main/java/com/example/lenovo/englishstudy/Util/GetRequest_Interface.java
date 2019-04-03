package com.example.lenovo.englishstudy.Util;

import com.example.lenovo.englishstudy.bean.WordSuggest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetRequest_Interface {

    @GET("suggest?le=eng&num=15&ver=2.0&doctype=json&keyfrom=mdict.7.2.0.android&model=honor&mid=5.6.1&imei=659135764921685&vendor=wandoujia&screen=1080x1800&ssid=superman&abtest=2")
    Call<WordSuggest> getCall(@Query("q") String word);
}
