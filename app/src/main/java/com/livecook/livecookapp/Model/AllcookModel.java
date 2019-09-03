package com.livecook.livecookapp.Model;

import java.util.List;

public class AllcookModel {


    /**
     * status : true
     * data : [{"id":7,"name":"WeSSaM Jibreen","country_id":5,"country_name":"Andorra","city_id":0,"city_name":"غير محدد","type_id":6,"type_name":"منفرد","full_mobile":"+3760597227390","description":"test description","region":"","avatar":"","avatar_url":"https://livecook.co/image","followers_no":1,"is_followed":false,"is_favorite":false},{"id":5,"name":"cooktest 3","country_id":5,"country_name":"Andorra","city_id":0,"city_name":"غير محدد","type_id":6,"type_name":"منفرد","full_mobile":"+376059722739203","description":"","region":"","avatar":"avatar.png","avatar_url":"https://livecook.co/image/avatar.png","followers_no":5,"is_followed":false,"is_favorite":false},{"id":3,"name":"WeSSaM Jibreen","country_id":5,"country_name":"Andorra","city_id":0,"city_name":"غير محدد","type_id":6,"type_name":"منفرد","full_mobile":"+3760597227391","description":"test description","region":"","avatar":"","avatar_url":"https://livecook.co/image","followers_no":1,"is_followed":false,"is_favorite":false},{"id":2,"name":"Salah Khattab","country_id":5,"country_name":"Andorra","city_id":0,"city_name":"غير محدد","type_id":6,"type_name":"منفرد","full_mobile":"+3760597227395","description":"","region":"","avatar":"","avatar_url":"https://livecook.co/image","followers_no":4,"is_followed":false,"is_favorite":false},{"id":1,"name":"WeSSaM Jibreen","country_id":5,"country_name":"Andorra","city_id":0,"city_name":"غير محدد","type_id":6,"type_name":"منفرد","full_mobile":"+3760597227393","description":"","region":"","avatar":"","avatar_url":"https://livecook.co/image","followers_no":1,"is_followed":false,"is_favorite":false}]
     * current_page : 1
     * first_page_url : https://livecook.co//api/v1/cooker/all?page=1
     * from : 1
     * last_page : 1
     * last_page_url : https://livecook.co//api/v1/cooker/all?page=1
     * next_page_url : null
     * path : https://livecook.co//api/v1/cooker/all
     * per_page : 6
     * prev_page_url : null
     * to : 5
     * total : 5
     */

    private boolean status;
    private int current_page;
    private String first_page_url;
    private int from;
    private int last_page;
    private String last_page_url;
    private Object next_page_url;
    private String path;
    private int per_page;
    private Object prev_page_url;
    private int to;
    private int total;
    private List<DataBean> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public String getFirst_page_url() {
        return first_page_url;
    }

    public void setFirst_page_url(String first_page_url) {
        this.first_page_url = first_page_url;
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

    public String getLast_page_url() {
        return last_page_url;
    }

    public void setLast_page_url(String last_page_url) {
        this.last_page_url = last_page_url;
    }

    public Object getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(Object next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public Object getPrev_page_url() {
        return prev_page_url;
    }

    public void setPrev_page_url(Object prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 7
         * name : WeSSaM Jibreen
         * country_id : 5
         * country_name : Andorra
         * city_id : 0
         * city_name : غير محدد
         * type_id : 6
         * type_name : منفرد
         * full_mobile : +3760597227390
         * description : test description
         * region :
         * avatar :
         * avatar_url : https://livecook.co/image
         * followers_no : 1
         * is_followed : false
         * is_favorite : false
         */

        private int id;
        private String name;
        private int country_id;
        private String country_name;
        private int city_id;
        private String city_name;
        private int type_id;
        private String type_name;
        private String full_mobile;
        private String description;
        private String region;
        private String avatar;
        private String avatar_url;
        private int followers_no;
        private boolean is_followed;
        private boolean is_favorite;

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

        public int getCountry_id() {
            return country_id;
        }

        public void setCountry_id(int country_id) {
            this.country_id = country_id;
        }

        public String getCountry_name() {
            return country_name;
        }

        public void setCountry_name(String country_name) {
            this.country_name = country_name;
        }

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getFull_mobile() {
            return full_mobile;
        }

        public void setFull_mobile(String full_mobile) {
            this.full_mobile = full_mobile;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
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

        public int getFollowers_no() {
            return followers_no;
        }

        public void setFollowers_no(int followers_no) {
            this.followers_no = followers_no;
        }

        public boolean isIs_followed() {
            return is_followed;
        }

        public void setIs_followed(boolean is_followed) {
            this.is_followed = is_followed;
        }

        public boolean isIs_favorite() {
            return is_favorite;
        }

        public void setIs_favorite(boolean is_favorite) {
            this.is_favorite = is_favorite;
        }
    }
}