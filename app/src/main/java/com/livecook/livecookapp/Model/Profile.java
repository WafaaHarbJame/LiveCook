package com.livecook.livecookapp.Model;

public class Profile {

    /**
     * status : true
     * data : {"id":12,"status_id":1,"name":"restwafaa","country_id":1,"mobile":"+930593121212","mobile_verified_at":null,"city_id":null,"region":null,"description":null,"rating":0,"avatar":null,"fcm_token":null,"is_notifications_activated":1,"created_at":"2019-07-29 09:54:51","updated_at":"2019-07-29 09:54:51","deleted_at":null}
     */

    private boolean status;
    private DataBean data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
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
         * id : 12
         * status_id : 1
         * name : restwafaa
         * country_id : 1
         * mobile : +930593121212
         * mobile_verified_at : null
         * city_id : null
         * region : null
         * description : null
         * rating : 0
         * avatar : null
         * fcm_token : null
         * is_notifications_activated : 1
         * created_at : 2019-07-29 09:54:51
         * updated_at : 2019-07-29 09:54:51
         * deleted_at : null
         */

        private int id;
        private int status_id;
        private String name;
        private int country_id;
        private String mobile;
        private Object mobile_verified_at;
        private Object city_id;
        private Object region;
        private Object description;
        private int rating;
        private Object avatar;
        private Object fcm_token;
        private int is_notifications_activated;
        private String created_at;
        private String updated_at;
        private Object deleted_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStatus_id() {
            return status_id;
        }

        public void setStatus_id(int status_id) {
            this.status_id = status_id;
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

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Object getMobile_verified_at() {
            return mobile_verified_at;
        }

        public void setMobile_verified_at(Object mobile_verified_at) {
            this.mobile_verified_at = mobile_verified_at;
        }

        public Object getCity_id() {
            return city_id;
        }

        public void setCity_id(Object city_id) {
            this.city_id = city_id;
        }

        public Object getRegion() {
            return region;
        }

        public void setRegion(Object region) {
            this.region = region;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
        }

        public Object getFcm_token() {
            return fcm_token;
        }

        public void setFcm_token(Object fcm_token) {
            this.fcm_token = fcm_token;
        }

        public int getIs_notifications_activated() {
            return is_notifications_activated;
        }

        public void setIs_notifications_activated(int is_notifications_activated) {
            this.is_notifications_activated = is_notifications_activated;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public Object getDeleted_at() {
            return deleted_at;
        }

        public void setDeleted_at(Object deleted_at) {
            this.deleted_at = deleted_at;
        }
    }
}