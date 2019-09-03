package com.livecook.livecookapp.Model;

public class CookModel {
    private int id;
    private  String cookimage;
    private  String cook_name;
    private  String cook_desc;
    private  String cook_address;
    private  String countfollow;

    public CookModel(int id, String cookimage, String cook_name, String cook_desc, String cook_address, String countfollow) {
        this.id = id;
        this.cookimage = cookimage;
        this.cook_name = cook_name;
        this.cook_desc = cook_desc;
        this.cook_address = cook_address;
        this.countfollow = countfollow;
    }


    public CookModel( String cookimage, String cook_name,
                      String cook_desc, String cook_address, String countfollow) {

        this.cookimage = cookimage;
        this.cook_name = cook_name;
        this.cook_desc = cook_desc;
        this.cook_address = cook_address;
        this.countfollow = countfollow;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }





    public String getCookimage() {
        return cookimage;
    }

    public void setCookimage(String cookimage) {
        this.cookimage = cookimage;
    }

    public String getCook_name() {
        return cook_name;
    }

    public void setCook_name(String cook_name) {
        this.cook_name = cook_name;
    }

    public String getCook_desc() {
        return cook_desc;
    }

    public void setCook_desc(String cook_desc) {
        this.cook_desc = cook_desc;
    }

    public String getCook_address() {
        return cook_address;
    }

    public void setCook_address(String cook_address) {
        this.cook_address = cook_address;
    }

    public String getCountfollow() {
        return countfollow;
    }

    public void setCountfollow(String countfollow) {
        this.countfollow = countfollow;
    }
}
