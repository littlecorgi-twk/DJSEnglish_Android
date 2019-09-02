package com.example.lenovo.englishstudy.bean;

public class LoginMessage {
    /**
     * status : 0
     * msg : 登录成功
     * data : {"user":{"id":22,"name":"手机用户18066877585","stage":"尚未填写","sex":"尚未填写","msg":"此人很懒, 尚未填写个人信息.","img":"http://www.zhangshuo.fun/images/default.jpg","phone":"18066877585"},"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjIsImV4cCI6MTU1NzU3NzA1NiwiaWF0IjoxNTU2OTcyMjU2fQ.2LShkqTBNT7N18mw-c30_3C7zUpRmnQSYbPGvWYXH2s"}
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
         * user : {"id":22,"name":"手机用户18066877585","stage":"尚未填写","sex":"尚未填写","msg":"此人很懒, 尚未填写个人信息.","img":"http://www.zhangshuo.fun/images/default.jpg","phone":"18066877585"}
         * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjIsImV4cCI6MTU1NzU3NzA1NiwiaWF0IjoxNTU2OTcyMjU2fQ.2LShkqTBNT7N18mw-c30_3C7zUpRmnQSYbPGvWYXH2s
         */

        private UserBean user;
        private String token;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public static class UserBean {
            /**
             * id : 22
             * name : 手机用户18066877585
             * stage : 尚未填写
             * sex : 尚未填写
             * msg : 此人很懒, 尚未填写个人信息.
             * img : http://www.zhangshuo.fun/images/default.jpg
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



}
