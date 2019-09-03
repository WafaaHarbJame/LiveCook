package com.livecook.livecookapp.Model;

public class MenuResturantModel {

    private String resturantmenu;
    private String image_name;

    public MenuResturantModel(String resturantmenu, String image_name) {
        this.resturantmenu = resturantmenu;
        this.image_name = image_name;
    }


    public String getResturantmenu() {
        return resturantmenu;
    }

    public void setResturantmenu(String resturantmenu) {
        this.resturantmenu = resturantmenu;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public MenuResturantModel() {
    }
}