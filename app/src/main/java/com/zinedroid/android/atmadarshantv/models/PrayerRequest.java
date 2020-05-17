package com.zinedroid.android.atmadarshantv.models;

import java.io.Serializable;

public class PrayerRequest implements Serializable {
    String id;
    String request_user_id;
    String requuest_user_name;

    public String getReq_count() {
        return req_count;
    }

    public void setReq_count(String req_count) {
        this.req_count = req_count;
    }
String place;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    String request_title;
    String request_discription;
    String req_count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequest_user_id() {
        return request_user_id;
    }

    public void setRequest_user_id(String request_user_id) {
        this.request_user_id = request_user_id;
    }

    public String getRequuest_user_name() {
        return requuest_user_name;
    }

    public void setRequuest_user_name(String requuest_user_name) {
        this.requuest_user_name = requuest_user_name;
    }

    public String getRequest_title() {
        return request_title;
    }

    public void setRequest_title(String request_title) {
        this.request_title = request_title;
    }

    public String getRequest_discription() {
        return request_discription;
    }

    public void setRequest_discription(String request_discription) {
        this.request_discription = request_discription;
    }
}
