package com.example.lenovo.englishstudy.bean;

import java.util.List;

public class FriendList {
    /**
     * status : 0
     * data : [{"id":23,"name":"Add","img":"null"}]
     */

    private int status;
    private List<DataBean> data;
    /**
     * msg : 好友数量为零
     */

    private String msg;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        public DataBean(int id, String name, String img) {
            this.id = id;
            this.name = name;
            this.img = img;
        }

        /**
         * id : 23
         * name : Add
         * img : null
         */

        private int id;
        private String name;
        private String img;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
