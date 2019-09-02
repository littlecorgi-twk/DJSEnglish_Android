package com.example.lenovo.englishstudy.bean;

public class QQLogin {

    /**
     * status : 0
     * msg : 验证成功
     * data : {"status":0,"msg":"登录成功","data":{"user":{"id":23,"name":"qqUser","sex":"尚未填写","stage":"尚未填写","msg":"此人很懒, 尚未填写个人信息.","img":"http://www.zhangshuo.fun/images/7243cb80-e769-4074-8de4-e6c687faf567.jpg","phone":"13227769909"},"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjMsImV4cCI6MTU1OTA0ODQ4OCwiaWF0IjoxNTU4NDQzNjg4fQ.q0rhWw-3BKweiRI6L1pVaT5aGhhtMTdEndh5Ln3o8nE"}}
     */

    private int status;
    private String msg;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * status : 0
         * msg : 登录成功
         * data : {"user":{"id":23,"name":"qqUser","sex":"尚未填写","stage":"尚未填写","msg":"此人很懒, 尚未填写个人信息.","img":"http://www.zhangshuo.fun/images/7243cb80-e769-4074-8de4-e6c687faf567.jpg","phone":"13227769909"},"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjMsImV4cCI6MTU1OTA0ODQ4OCwiaWF0IjoxNTU4NDQzNjg4fQ.q0rhWw-3BKweiRI6L1pVaT5aGhhtMTdEndh5Ln3o8nE"}
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
             * user : {"id":23,"name":"qqUser","sex":"尚未填写","stage":"尚未填写","msg":"此人很懒, 尚未填写个人信息.","img":"http://www.zhangshuo.fun/images/7243cb80-e769-4074-8de4-e6c687faf567.jpg","phone":"13227769909"}
             * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjMsImV4cCI6MTU1OTA0ODQ4OCwiaWF0IjoxNTU4NDQzNjg4fQ.q0rhWw-3BKweiRI6L1pVaT5aGhhtMTdEndh5Ln3o8nE
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
                 * id : 23
                 * name : qqUser
                 * sex : 尚未填写
                 * stage : 尚未填写
                 * msg : 此人很懒, 尚未填写个人信息.
                 * img : http://www.zhangshuo.fun/images/7243cb80-e769-4074-8de4-e6c687faf567.jpg
                 * phone : 13227769909
                 */

                private int id;
                private String name;
                private String sex;
                private String stage;
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

                public String getSex() {
                    return sex;
                }

                public void setSex(String sex) {
                    this.sex = sex;
                }

                public String getStage() {
                    return stage;
                }

                public void setStage(String stage) {
                    this.stage = stage;
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
}
