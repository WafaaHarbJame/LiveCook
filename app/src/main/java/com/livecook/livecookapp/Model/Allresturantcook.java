package com.livecook.livecookapp.Model;

public class Allresturantcook {

    private int id;
    private  String allimage;
    private  String countall;
    private  String livetitle;
    private  String allname;

    public Allresturantcook(int id, String allimage, String countall, String livetitle, String allname) {

        this.id = id;
        this.allimage = allimage;
        this.countall = countall;
        this.livetitle = livetitle;
        this.allname = allname;
    }

    public Allresturantcook(String allimage, String countall, String livetitle, String allname) {
        this.allimage = allimage;
        this.countall = countall;
        this.livetitle = livetitle;
        this.allname = allname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAllimage() {
        return allimage;
    }

    public void setAllimage(String allimage) {
        this.allimage = allimage;
    }

    public String getCountall() {
        return countall;
    }

    public void setCountall(String countall) {
        this.countall = countall;
    }

    public String getLivetitle() {
        return livetitle;
    }

    public void setLivetitle(String livetitle) {
        this.livetitle = livetitle;
    }

    public String getAllname() {
        return allname;
    }

    public void setAllname(String allname) {
        this.allname = allname;
    }
}
