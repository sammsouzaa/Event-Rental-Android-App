package com.example.lazerrenttest.model;

import android.net.Uri;

public class RecyclerMyLocations {

    String title, status, type, ID;
    Uri imageUrl;

    public RecyclerMyLocations(){

    }

    public RecyclerMyLocations(String title, String status, String type, String ID, Uri imageUrl ) {
        this.title = title;
        this.status = status;
        this.type = type;
        this.ID = ID;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Uri getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Uri imageUrl) {
        this.imageUrl = imageUrl;
    }
}