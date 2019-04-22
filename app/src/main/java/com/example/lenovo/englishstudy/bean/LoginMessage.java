package com.example.lenovo.englishstudy.bean;

public class LoginMessage {

    /**
     * status : 0
     * msg : 登录成功
     * data : {"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjIsImV4cCI6MTU1NjM1NDU1MiwiaWF0IjoxNTU1NzQ5NzUyfQ.Wz4bW7dCBoUBbMkKhDTxNWrTPB5AX-KS0PRgkfuZ-y0","user":{"id":22,"name":"小张","msg":"后台工作人员","img":"ftp://47.102.206.19/4fd3662b-0c8b-4caf-8d8d-9344ec5f12b6.png","email":"9447331421@qq.com","phone":"13227769909"}}
     */

    private int status;
    private String msg;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjIsImV4cCI6MTU1NjM1NDU1MiwiaWF0IjoxNTU1NzQ5NzUyfQ.Wz4bW7dCBoUBbMkKhDTxNWrTPB5AX-KS0PRgkfuZ-y0
         * user : {"id":22,"name":"小张","msg":"后台工作人员","img":"ftp://47.102.206.19/4fd3662b-0c8b-4caf-8d8d-9344ec5f12b6.png","email":"9447331421@qq.com","phone":"13227769909"}
         */

        private String token;
        private UserBean user;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * id : 22
             * name : 小张
             * msg : 后台工作人员
             * img : ftp://47.102.206.19/4fd3662b-0c8b-4caf-8d8d-9344ec5f12b6.png
             * email : 9447331421@qq.com
             * phone : 13227769909
             */

            private int id;
            private String name;
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
}
