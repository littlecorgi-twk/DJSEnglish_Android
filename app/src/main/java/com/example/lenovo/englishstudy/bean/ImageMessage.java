package com.example.lenovo.englishstudy.bean;

public class ImageMessage {

    /**
     * status : 0
     * data : {"uri":"708c3f48-cb6c-448d-8909-253665ab17b0.png","url":"ftp://47.102.206.19/708c3f48-cb6c-448d-8909-253665ab17b0.png"}
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
         * uri : 708c3f48-cb6c-448d-8909-253665ab17b0.png
         * url : ftp://47.102.206.19/708c3f48-cb6c-448d-8909-253665ab17b0.png
         */

        private String uri;
        private String url;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
