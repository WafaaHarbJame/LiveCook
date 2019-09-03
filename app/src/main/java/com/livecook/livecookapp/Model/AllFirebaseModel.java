package com.livecook.livecookapp.Model;

import java.util.Date;

public class AllFirebaseModel {

    private String LivePath;
    private String RecordPath;
    private String WatchPath;
    private int count;
    private String full_mobile;
    private int id;
    private String name;
    private boolean status;
    private String title;
    private int type;
    private int watch;
    private String created_at;
    private String LiveImage;

    public AllFirebaseModel() {
    }

    public AllFirebaseModel(String livePath, String recordPath, String watchPath, int count, String full_mobile, int id, String name, boolean status, String title, int type, int watch, String created_at, String liveImage) {
        LivePath = livePath;
        RecordPath = recordPath;
        WatchPath = watchPath;
        this.count = count;
        this.full_mobile = full_mobile;
        this.id = id;
        this.name = name;
        this.status = status;
        this.title = title;
        this.type = type;
        this.watch = watch;
        this.created_at = created_at;
        LiveImage = liveImage;
    }

    public String getLivePath() {
        return LivePath;
    }

    public void setLivePath(String livePath) {
        LivePath = livePath;
    }

    public String getRecordPath() {
        return RecordPath;
    }

    public void setRecordPath(String recordPath) {
        RecordPath = recordPath;
    }

    public String getWatchPath() {
        return WatchPath;
    }

    public void setWatchPath(String watchPath) {
        WatchPath = watchPath;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getFull_mobile() {
        return full_mobile;
    }

    public void setFull_mobile(String full_mobile) {
        this.full_mobile = full_mobile;
    }

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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getWatch() {
        return watch;
    }

    public void setWatch(int watch) {
        this.watch = watch;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLiveImage() {
        return LiveImage;
    }

    public void setLiveImage(String liveImage) {
        LiveImage = liveImage;
    }
}