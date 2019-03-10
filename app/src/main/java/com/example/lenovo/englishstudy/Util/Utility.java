package com.example.lenovo.englishstudy.Util;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * @author littlecorgi
 * @Date 2019-03-10 19:30
 * @email a1203991686@126.com
 */
public class Utility {

    public static boolean handleWordMeaningRequest (String request) {
        if (!TextUtils.isEmpty(request)) {
            try {
                JSONArray wordMeaning = new JSONArray(request);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
