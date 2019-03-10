package com.example.lenovo.englishstudy.Util;

import android.text.TextUtils;

import com.example.lenovo.englishstudy.bean.WordMeanig;
import com.google.gson.Gson;

import org.json.JSONArray;
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
}
