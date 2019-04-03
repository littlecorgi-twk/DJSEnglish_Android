package com.example.lenovo.englishstudy.bean;

import java.util.List;

public class WordSuggest {

    /**
     * result : {"msg":"success","code":200}
     * data : {"entries":[{"explain":"art. 一; 任一; 每一","entry":"a"},{"explain":"n. 账户; 解释; 账目，账单; 理由; vi. 解释; 导致; 报账; vt. 认为; 把\u2026视为","entry":"account"},{"explain":"adj. 有效的，可得的; 可利用的; 空闲的","entry":"available"},{"explain":"vt. 欣赏; 感激; 领会; 鉴别; vi. 增值; 涨价","entry":"appreciate"},{"explain":"vt. 使用; 存取; 接近; n. 进入; 使用权; 通路","entry":"access"},{"explain":"vt. 承担; 假定; 采取; 呈现; vi. 装腔作势; 多管闲事","entry":"assume"},{"explain":"adj. 适当的; vt. 占用; 拨出","entry":"appropriate"},{"explain":"vt. 演说; 从事; 忙于; 写姓名地址; n. 地址; 演讲; 致辞; 说话的技巧","entry":"address"},{"explain":"n. 方法; 途径; 接近; vt. 接近; 着手处理; vi. 靠近","entry":"approach"},{"explain":"adj. 供选择的; 选择性的; 交替的; n. 二中择一; 供替代的选择","entry":"alternative"},{"explain":"n. 发展; 前进; 增长; 预付款; vt. 提出; 预付; 使\u2026\u2026前进; 将\u2026\u2026提前; vi. ...","entry":"advance"},{"explain":"vi. 交往; 结交; n. 同事，伙伴; 关联的事物; vt. 联想; 使联合; 使发生联系; a...","entry":"associate"},{"explain":"vt. 申请; 涂，敷; 应用; vi. 申请; 涂，敷; 适用; 请求","entry":"apply"},{"explain":"vt. 影响; 感染; 感动; 假装; vi. 倾向; 喜欢; n. 情感; 引起感情的因素","entry":"affect"},{"explain":"n. 属性; 特质; vt. 归属; 把\u2026归于","entry":"attribute"}],"query":"a","language":"eng","type":"dict"}
     */

    private ResultBean result;
    private DataBean data;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class ResultBean {
        /**
         * msg : success
         * code : 200
         */

        private String msg;
        private int code;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    public static class DataBean {
        /**
         * entries : [{"explain":"art. 一; 任一; 每一","entry":"a"},{"explain":"n. 账户; 解释; 账目，账单; 理由; vi. 解释; 导致; 报账; vt. 认为; 把\u2026视为","entry":"account"},{"explain":"adj. 有效的，可得的; 可利用的; 空闲的","entry":"available"},{"explain":"vt. 欣赏; 感激; 领会; 鉴别; vi. 增值; 涨价","entry":"appreciate"},{"explain":"vt. 使用; 存取; 接近; n. 进入; 使用权; 通路","entry":"access"},{"explain":"vt. 承担; 假定; 采取; 呈现; vi. 装腔作势; 多管闲事","entry":"assume"},{"explain":"adj. 适当的; vt. 占用; 拨出","entry":"appropriate"},{"explain":"vt. 演说; 从事; 忙于; 写姓名地址; n. 地址; 演讲; 致辞; 说话的技巧","entry":"address"},{"explain":"n. 方法; 途径; 接近; vt. 接近; 着手处理; vi. 靠近","entry":"approach"},{"explain":"adj. 供选择的; 选择性的; 交替的; n. 二中择一; 供替代的选择","entry":"alternative"},{"explain":"n. 发展; 前进; 增长; 预付款; vt. 提出; 预付; 使\u2026\u2026前进; 将\u2026\u2026提前; vi. ...","entry":"advance"},{"explain":"vi. 交往; 结交; n. 同事，伙伴; 关联的事物; vt. 联想; 使联合; 使发生联系; a...","entry":"associate"},{"explain":"vt. 申请; 涂，敷; 应用; vi. 申请; 涂，敷; 适用; 请求","entry":"apply"},{"explain":"vt. 影响; 感染; 感动; 假装; vi. 倾向; 喜欢; n. 情感; 引起感情的因素","entry":"affect"},{"explain":"n. 属性; 特质; vt. 归属; 把\u2026归于","entry":"attribute"}]
         * query : a
         * language : eng
         * type : dict
         */

        private String query;
        private String language;
        private String type;
        private List<EntriesBean> entries;

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<EntriesBean> getEntries() {
            return entries;
        }

        public void setEntries(List<EntriesBean> entries) {
            this.entries = entries;
        }

        public static class EntriesBean {

            /**
             * explain : art. 一; 任一; 每一
             * entry : a
             */

            public EntriesBean(String entry, String explain) {
                this.explain = explain;
                this.entry = entry;
            }

            private String explain;
            private String entry;

            public String getExplain() {
                return explain;
            }

            public void setExplain(String explain) {
                this.explain = explain;
            }

            public String getEntry() {
                return entry;
            }

            public void setEntry(String entry) {
                this.entry = entry;
            }
        }
    }
}

