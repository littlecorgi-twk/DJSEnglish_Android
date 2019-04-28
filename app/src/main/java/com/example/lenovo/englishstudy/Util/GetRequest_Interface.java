package com.example.lenovo.englishstudy.Util;

import com.example.lenovo.englishstudy.bean.ArticleDetail;
import com.example.lenovo.englishstudy.bean.ArticleList;
import com.example.lenovo.englishstudy.bean.ImageMessage;
import com.example.lenovo.englishstudy.bean.LoginMessage;
import com.example.lenovo.englishstudy.bean.MessageVerify;
import com.example.lenovo.englishstudy.bean.UserMessage;
import com.example.lenovo.englishstudy.bean.WordList;
import com.example.lenovo.englishstudy.bean.WordSuggest;
import com.example.lenovo.englishstudy.bean.WordSuggestDetail;
import com.example.lenovo.englishstudy.bean.WordTranslate;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface GetRequest_Interface {

    @GET("suggest?le=eng&num=15&ver=2.0&doctype=json&keyfrom=mdict.7.2.0.android&model=honor&mid=5.6.1&imei=659135764921685&vendor=wandoujia&screen=1080x1800&ssid=superman&abtest=2")
    Call<WordSuggest> getWordSuggestCall(@Query("q") String word_wordSuggest);

    @GET("translate?&from=en&to=zh&appid=20180809000192979&salt=1435660288")
    Call<WordTranslate> getWordTranslateCall(@Query("q") String word, @Query("sign") String md5);

    @GET("jsonapi?jsonversion=2&client=mobile&dicts=%7B\"count\"%3A99%2C\"dicts\"%3A%5B%5B\"ec\"%2C\"ce\"%2C\"newcj\"%2C\"newjc\"%2C\"kc\"%2C\"ck\"%2C\"fc\"%2C\"cf\"%2C\"multle\"%2C\"jtj\"%2C\"pic_dict\"%2C\"tc\"%2C\"ct\"%2C\"typos\"%2C\"special\"%2C\"tcb\"%2C\"baike\"%2C\"lang\"%2C\"simple\"%2C\"wordform\"%2C\"exam_dict\"%2C\"ctc\"%2C\"web_search\"%2C\"auth_sents_part\"%2C\"ec21\"%2C\"phrs\"%2C\"input\"%2C\"wikipedia_digest\"%2C\"ee\"%2C\"collins\"%2C\"ugc\"%2C\"media_sents_part\"%2C\"syno\"%2C\"rel_word\"%2C\"longman\"%2C\"ce_new\"%2C\"le\"%2C\"newcj_sents\"%2C\"blng_sents_part\"%2C\"hh\"%5D%2C%5B\"ugc\"%5D%2C%5B\"longman\"%5D%2C%5B\"newjc\"%5D%2C%5B\"newcj\"%5D%2C%5B\"web_trans\"%5D%2C%5B\"fanyi\"%5D%5D%7D&keyfrom=mdict.7.2.0.android&model=honor&mid=5.6.1&imei=659135764921685&vendor=wandoujia&screen=1080x1800&ssid=superman&network=wifi&abtest=2&xmlVersion=5.1")
    Call<WordSuggestDetail> getWordSuggestDetailCall(@Query("q") String word);

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("get_msgcode.do")
    Call<MessageVerify> getMessageVerifyCall(@Field("phoneNumber") String phoneNumber);

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("register.do")
    Call<MessageVerify> getRegisterCall(@Field("phone") String phone, @Field("password") String password, @Field("msgCode") String msgCode);

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("login.do")
    Call<LoginMessage> getLoginCall(@Field("phoneNumber") String phoneNumber, @Field("password") String password);
    
    @Multipart
//    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("upload.do")
    Call<ImageMessage> upload(@Part MultipartBody.Part file, @Header("token") String token);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("get_words.do")
    Call<WordList> getWordListCall();

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("update_user_info.do")
    Call<UserMessage> updateMessageCall(@Header("token") String token);

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("check_username.do")
    Call<MessageVerify> getUserNameCall(@Field("userName") String userName);

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("forget_reset_password.do")
    Call<MessageVerify> forgetPasswordCall(@Field("phoneNumber") String phoneNumber, @Field("msgCode") String msgCode, @Field("password") String password);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("article/get_list.do")
    Call<ArticleList> getArticleList();

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("article/get_detail.do")
    Call<ArticleDetail> getArticleDetail(@Field("articleId") int id);

}
