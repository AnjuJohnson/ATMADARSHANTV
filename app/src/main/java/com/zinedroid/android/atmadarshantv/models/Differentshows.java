package com.zinedroid.android.atmadarshantv.models;

import java.util.ArrayList;

public class Differentshows {
    String showid;
    String showname;
    String image;
    boolean isfavourite;

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    String discription;
    public boolean isIsfavourite() {
        return isfavourite;
    }

    public void setIsfavourite(boolean isfavourite) {
        this.isfavourite = isfavourite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    ArrayList<Video> showdetaillist;

    public String getShowid() {
        return showid;
    }

    public void setShowid(String showid) {
        this.showid = showid;
    }

    public String getShowname() {
        return showname;
    }

    public void setShowname(String showname) {
        this.showname = showname;
    }

    public ArrayList<Video> getShowdetaillist() {
        return showdetaillist;
    }

    public void setShowdetaillist(ArrayList<Video> showdetaillist) {
        this.showdetaillist = showdetaillist;
    }
}
