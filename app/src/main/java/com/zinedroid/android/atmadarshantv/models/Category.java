package com.zinedroid.android.atmadarshantv.models;

import java.util.ArrayList;

public class Category {
    String cat_id;
    String count;
    ArrayList<Video> videolonk;
    String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ArrayList<Video> getVideolonk() {
        return videolonk;
    }

    public void setVideolonk(ArrayList<Video> videolonk) {
        this.videolonk = videolonk;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCat_name() {

        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    String cat_name;
}
