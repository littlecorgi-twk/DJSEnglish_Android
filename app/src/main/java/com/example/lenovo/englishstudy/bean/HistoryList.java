package com.example.lenovo.englishstudy.bean;

import java.util.List;

/**
 * @author littlecorgi
 * @Date 2019-04-17 20:03
 * @email a1203991686@126.com
 */
public class HistoryList {

    /**
     * status : 0
     * data : [{"user":1,"word":"hello123"},{"user":1,"word":"hello12"},{"user":1,"word":"hello1"},{"user":1,"word":"hello"}]
     */

    private int status;
    private List<DataBean> data;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user : 1
         * word : hello123
         */

        private int user;
        private String word;

        public int getUser() {
            return user;
        }

        public void setUser(int user) {
            this.user = user;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }
    }
}
