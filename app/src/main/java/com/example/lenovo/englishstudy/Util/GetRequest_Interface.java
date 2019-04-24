package com.example.lenovo.englishstudy.Util;

import com.example.lenovo.englishstudy.bean.ArticleList;
import com.example.lenovo.englishstudy.bean.ImageMessage;
import com.example.lenovo.englishstudy.bean.LoginMessage;
import com.example.lenovo.englishstudy.bean.MessageVerify;
import com.example.lenovo.englishstudy.bean.UserMessage;
import com.example.lenovo.englishstudy.bean.WordList;
import com.example.lenovo.englishstudy.bean.WordMeanig;
import com.example.lenovo.englishstudy.bean.WordSuggest;
import com.example.lenovo.englishstudy.bean.WordSuggestDetail;
import com.example.lenovo.englishstudy.bean.WordTranslate;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface GetRequest_Interface {

    @GET("suggest?le=eng&num=15&ver=2.0&doctype=json&keyfrom=mdict.7.2.0.android&model=honor&mid=5.6.1&imei=659135764921685&vendor=wandoujia&screen=1080x1800&ssid=superman&abtest=2")
    Call<WordSuggest> getWordSuggestCall(@Query("q") String word_wordSuggest);

    @GET("jsonapi?jsonversion=2&client=mobile&dicts=%7B%22count%22%3A99%2C%22dicts%22%3A%5B%5B%22ec%22%2C%22ce%22%2C%22newcj%22%2C%22newjc%22%2C%22kc%22%2C%22ck%22%2C%22fc%22%2C%22cf%22%2C%22multle%22%2C%22jtj%22%2C%22pic_dict%22%2C%22tc%22%2C%22ct%22%2C%22typos%22%2C%22special%22%2C%22tcb%22%2C%22baike%22%2C%22lang%22%2C%22simple%22%2C%22wordform%22%2C%22exam_dict%22%2C%22ctc%22%2C%22web_search%22%2C%22auth_sents_part%22%2C%22ec21%22%2C%22phrs%22%2C%22input%22%2C%22wikipedia_digest%22%2C%22ee%22%2C%22collins%22%2C%22ugc%22%2C%22media_sents_part%22%2C%22syno%22%2C%22rel_word%22%2C%22longman%22%2C%22ce_new%22%2C%22le%22%2C%22newcj_sents%22%2C%22blng_sents_part%22%2C%22hh%22%5D%2C%5B%22ugc%22%5D%2C%5B%22longman%22%5D%2C%5B%22newjc%22%5D%2C%5B%22newcj%22%5D%2C%5B%22web_trans%22%5D%2C%5B%22fanyi%22%5D%5D%7D&keyfrom=mdict.7.2.0.android&model=honor&mid=5.6.1&imei=659135764921685&vendor=wandoujia&screen=1080x1800&ssid=superman&network=wifi&abtest=2&xmlVersion=5.1")
    Call<WordMeanig> getWordMeaningCall(@Query("q") String word_wordMeaning);

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
    @POST("upload.do")
    Call<ImageMessage> upload(@Part MultipartBody.Part file);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("get_words.do")
    Call<WordList> getWordListCall();

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("get_user_info.do")
    Call<UserMessage> getMessageCall();

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("article/get_list.do")
    Call<ArticleList> getArticleList();

}
