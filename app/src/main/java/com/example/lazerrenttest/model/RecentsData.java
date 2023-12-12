package com.example.lazerrenttest.model;


import android.net.Uri;

public class RecentsData {

    private String title, address,price, ID, status, type;

    private Uri photo;


    public RecentsData(String title, String address, String price, String ID, String status,String type, Uri photo) {
        this.title = title;
        this.address = address;
        this.price = price;
        this.ID = ID;
        this.status = status;
        this.type = type;
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
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
}