package com.livecook.livecookapp.Model;

import java.util.List;

public class NotificationModel {


    /**
     * status : true
     * from : 1
     * last_page : 1
     * total : 1
     * to : 1
     * per_page : 10
     * data : [{"firebase_path":"/test/path/to/firebase","title":"بث مباشر جديد","message":" الطباخ صابرين لديه بث مباشر الآن","avatar":"avatar.png","avatar_url":"https://livecook.co/image/40x40/avatar.png","from_id":5,"type":"new_live","id":"311b2885-cef9-4548-947a-678e1bc69369","created_at":"2019-08-15 08:45:12","read_at":""}]
     */

    private boolean status;
    private int from;
    private int last_page;
    private int total;
    private int to;
    private int per_page;
    private List<DataBean> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * firebase_path : /test/path/to/firebase
         * title : بث مباشر جديد
         * message :  الطباخ صابرين لديه بث مباشر الآن
         * avatar : avatar.png
         * avatar_url : https://livecook.co/image/40x40/avatar.png
         * from_id : 5
         * type : new_live
         * id : 311b2885-cef9-4548-947a-678e1bc69369
         * created_at : 2019-08-15 08:45:12
         * read_at :
         */

        private String firebase_path;
        private String title;
        private String message;
        private String avatar;
        private String avatar_url;
        private int from_id;
        private String type;
        private String id;
        private String created_at;
        private String read_at;

        public String getFirebase_path() {
            return firebase_path;
        }

        public void setFirebase_path(String firebase_path) {
            this.firebase_path = firebase_path;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public int getFrom_id() {
            return from_id;
        }

        public void setFrom_id(int from_id) {
            this.from_id = from_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getRead_at() {
            return read_at;
        }

        public void setRead_at(String read_at) {
            this.read_at = read_at;
        }
    }
}