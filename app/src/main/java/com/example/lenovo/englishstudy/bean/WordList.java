package com.example.lenovo.englishstudy.bean;

import java.util.List;

public class WordList {

    /**
     * status : 0
     * data : [{"id":23,"word":"hello2","soundMark":"test2","pos":"test2","updateTime":1554329779000,"createTime":1554329779000,"mean":"你好2"}]
     */

    private int status;
    private List<DataBean> data;

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
         * id : 23
         * word : hello2
         * soundMark : test2
         * pos : test2
         * updateTime : 1554329779000
         * createTime : 1554329779000
         * mean : 你好2
         */

        private int id;
        private String word;
        private String soundMark;
        private String pos;
        private String updateTime;
        private String createTime;
        private String mean;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getSoundMark() {
            return soundMark;
        }

        public void setSoundMark(String soundMark) {
            this.soundMark = soundMark;
        }

        public String getPos() {
            return pos;
        }

        public void setPos(String pos) {
            this.pos = pos;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getMean() {
            return mean;
        }

        public void setMean(String mean) {
            this.mean = mean;
        }
    }

    @Override
    public String toString() {
        return "WordList{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}
