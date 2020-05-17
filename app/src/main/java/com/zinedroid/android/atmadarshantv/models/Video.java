package com.zinedroid.android.atmadarshantv.models;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Video extends SugarRecord<Video> implements Serializable {
    String cat_id;
    String cat_name;
    String videolink;
String video_titile;
String discription;
boolean isfavourite;
String idd;
String views;
String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }

    public boolean isIsfavourite() {
        return isfavourite;
    }

    public void setIsfavourite(boolean isfavourite) {
        this.isfavourite = isfavourite;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription)  {
        this.discription = discription;
    }

    public String getVideo_titile() {
        return video_titile;
    }

    public void setVideo_titile(String video_titile) {
        this.video_titile = video_titile;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getVideolink() {
        return videolink;
    }

    public void setVideolink(String videolink) {
        this.videolink = videolink;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }
}
