package com.example.countbunny.launchmodetest1.bitmapdecode;

import java.util.List;

public class BeautyRootBean {

    //http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1
    public boolean error;

    public List<BeautyBean> results;

    public static class BeautyBean{

        /**
         * _id : 5a8e0c41421aa9133298a259
         * createdAt : 2018-02-22T08:18:09.547Z
         * desc : 2-22
         * publishedAt : 2018-02-22T08:24:35.209Z
         * source : chrome
         * type : 福利
         * url : https://ws1.sinaimg.cn/large/610dc034ly1foowtrkpvkj20sg0izdkx.jpg
         * used : true
         * who : 代码家
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }

}
