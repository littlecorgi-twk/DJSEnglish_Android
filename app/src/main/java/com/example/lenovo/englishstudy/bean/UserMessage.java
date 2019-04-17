package com.example.lenovo.englishstudy.bean;

public class UserMessage {

    /**
     * status : 0
     * data : {"id":1,"name":"zhang","username":"zhangshuo123","msg":"aaa","img":"null","email":"944733142@qq.com","phone":"18066877585"}
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
         * id : 1
         * name : zhang
         * username : zhangshuo123
         * msg : aaa
         * img : null
         * email : 944733142@qq.com
         * phone : 18066877585
         */

        private int id;
        private String name;
        private String username;
        private String msg;
        private String img;
        private String email;
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
