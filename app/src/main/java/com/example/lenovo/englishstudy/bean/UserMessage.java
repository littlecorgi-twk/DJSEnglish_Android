package com.example.lenovo.englishstudy.bean;

public class UserMessage {

    /**
     * status : 0
     * data : {"id":22,"name":"张","stage":"男","sex":"小学二年级","msg":"后台工作人员","img":"http://47.102.206.19/images/default.jpg","phone":"18066877585"}
     */

    private int status;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 22
         * name : 张
         * stage : 男
         * sex : 小学二年级
         * msg : 后台工作人员
         * img : http://47.102.206.19/images/default.jpg
         * phone : 18066877585
         */

        private int id;
        private String name;
        private String stage;
        private String sex;
        private String msg;
        private String img;
        private String phone;

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

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
