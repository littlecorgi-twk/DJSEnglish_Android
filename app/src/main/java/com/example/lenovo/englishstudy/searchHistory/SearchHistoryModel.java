package com.example.lenovo.englishstudy.searchHistory;

import java.io.Serializable;

/**
 * @author littlecorgi
 * @Date 2019-04-14 22:53
 * @email a1203991686@126.com
 */
public class SearchHistoryModel implements Serializable {
    private String time;
    private String content;

    public SearchHistoryModel() {
    }

    public SearchHistoryModel(String time, String content) {

        this.content = content;
        this.time = time;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
