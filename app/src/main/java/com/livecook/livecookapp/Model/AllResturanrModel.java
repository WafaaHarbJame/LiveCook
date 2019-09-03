package com.livecook.livecookapp.Model;

import java.util.List;

public class AllResturanrModel {


    /**
     * status : true
     * data : [{"id":20,"name":"WeSSaM Jibreen","country_id":4,"country_name":"American Samoa","city_id":0,"city_name":"غير محدد","full_mobile":"+16840594444444","region":"","description":"test description","municipal_license":"https://livecook.co/image/image_15646448222086894697.png","avatar":"","avatar_url":"https://livecook.co/image","followers_no":0,"is_followed":false,"is_favorite":false},{"id":19,"name":"Palmera","country_id":1,"country_name":"Afghanistan","city_id":0,"city_name":"غير محدد","full_mobile":"+9305972273933","region":"","description":"","municipal_license":"https://livecook.co/image","avatar":"avatar.png","avatar_url":"https://livecook.co/image/avatar.png","followers_no":1,"is_followed":false,"is_favorite":false},{"id":18,"name":"مطعم  اختبار1112222ggghgg","country_id":2,"country_name":"Albania","city_id":1,"city_name":"Andaman and Nicobar Islands","full_mobile":"+35505932325417","region":"gfx","description":"test description hhnjjnvffdx","municipal_license":"https://livecook.co/image/image_1565140754430398797.jpg","avatar":"","avatar_url":"https://livecook.co/image","followers_no":0,"is_followed":false,"is_favorite":false},{"id":17,"name":"Palmera5","country_id":1,"country_name":"Afghanistan","city_id":0,"city_name":"غير محدد","full_mobile":"+930599028444","region":"","description":"","municipal_license":"https://livecook.co/image/image_1565008044628327772.png","avatar":"avatar.png","avatar_url":"https://livecook.co/image/avatar.png","followers_no":0,"is_followed":false,"is_favorite":false},{"id":16,"name":"Palmera","country_id":5,"country_name":"Andorra","city_id":0,"city_name":"غير محدد","full_mobile":"+3760597227322","region":"","description":"","municipal_license":"https://livecook.co/image/image_15646448222086894697.png","avatar":"avatar.png","avatar_url":"https://livecook.co/image/avatar.png","followers_no":1,"is_followed":false,"is_favorite":false},{"id":15,"name":"Palmera","country_id":5,"country_name":"Andorra","city_id":0,"city_name":"غير محدد","full_mobile":"+3760597227321","region":"","description":"","municipal_license":"https://livecook.co/image/image_15646448222086894697.png","avatar":"avatar.png","avatar_url":"https://livecook.co/image/avatar.png","followers_no":0,"is_followed":false,"is_favorite":false}]
     * current_page : 1
     * first_page_url : https://livecook.co//api/v1/restaurant/all?page=1
     * from : 1
     * last_page : 4
     * last_page_url : https://livecook.co//api/v1/restaurant/all?page=4
     * next_page_url : https://livecook.co//api/v1/restaurant/all?page=2
     * path : https://livecook.co//api/v1/restaurant/all
     * per_page : 6
     * prev_page_url : null
     * to : 6
     * total : 20
     */

    private boolean status;
    private int current_page;
    private String first_page_url;
    private int from;
    private int last_page;
    private String last_page_url;
    private String next_page_url;
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

    public String getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
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
         * id : 20
         * name : WeSSaM Jibreen
         * country_id : 4
         * country_name : American Samoa
         * city_id : 0
         * city_name : غير محدد
         * full_mobile : +16840594444444
         * region :
         * description : test description
         * municipal_license : https://livecook.co/image/image_15646448222086894697.png
         * avatar :
         * avatar_url : https://livecook.co/image
         * followers_no : 0
         * is_followed : false
         * is_favorite : false
         */

        private int id;
        private String name;
        private int country_id;
        private String country_name;
        private int city_id;
        private String city_name;
        private String full_mobile;
        private String region;
        private String description;
        private String municipal_license;
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

        public String getFull_mobile() {
            return full_mobile;
        }

        public void setFull_mobile(String full_mobile) {
            this.full_mobile = full_mobile;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMunicipal_license() {
            return municipal_license;
        }

        public void setMunicipal_license(String municipal_license) {
            this.municipal_license = municipal_license;
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
