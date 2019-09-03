package com.livecook.livecookapp.Model;

public class Notification {
    private int id;
    private  String time;
    private  String notficatio_image;
    private  String notificaton_title;
    private  String notificatio_descrption;

    public Notification(int id, String time, String notficatio_image, String notificaton_title, String notificatio_descrption) {
        this.id = id;
        this.time = time;
        this.notficatio_image = notficatio_image;
        this.notificaton_title = notificaton_title;
        this.notificatio_descrption = notificatio_descrption;
    }

    public Notification(String time, String notficatio_image, String notificaton_title, String notificatio_descrption) {
        this.time = time;
        this.notficatio_image = notficatio_image;
        this.notificaton_title = notificaton_title;
        this.notificatio_descrption = notificatio_descrption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNotficatio_image() {
        return notficatio_image;
    }

    public void setNotficatio_image(String notficatio_image) {
        this.notficatio_image = notficatio_image;
    }

    public String getNotificaton_title() {
        return notificaton_title;
    }

    public void setNotificaton_title(String notificaton_title) {
        this.notificaton_title = notificaton_title;
    }

    public String getNotificatio_descrption() {
        return notificatio_descrption;
    }

    public void setNotificatio_descrption(String notificatio_descrption) {
        this.notificatio_descrption = notificatio_descrption;
    }
}
