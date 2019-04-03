package com.example.lenovo.englishstudy.Util;

import com.example.lenovo.englishstudy.bean.MessageVerify;
import com.example.lenovo.englishstudy.bean.WordMeanig;
import com.example.lenovo.englishstudy.bean.WordSuggest;
import com.example.lenovo.englishstudy.bean.WordTranslate;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author littlecorgi
 * @Date 2019-03-10 19:30
 * @email a1203991686@126.com
 */
public class Utility {

    public static WordMeanig handleWordMeaningResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String wordMeaningContent = jsonObject.toString();
            return new Gson().fromJson(wordMeaningContent, WordMeanig.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static WordTranslate handleWordTranslateResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String wordTranslateContent = jsonObject.toString();
            return new Gson().fromJson(wordTranslateContent, WordTranslate.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static WordSuggest handleWordSuggestResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String wordSuggestContent = jsonObject.toString();
            return new Gson().fromJson(wordSuggestContent, WordSuggest.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static MessageVerify handleMessageVerifyResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String messageVerifyContent = jsonObject.toString();
            return new Gson().fromJson(messageVerifyContent, MessageVerify.class);
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
