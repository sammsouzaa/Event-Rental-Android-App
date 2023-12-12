package com.example.lazerrenttest.model;

import android.net.Uri;

public class RecyclerUserModel {
    private String userId, fullname, email, imoveis;

    private Uri photoUser;

    public RecyclerUserModel() {
    }

    public RecyclerUserModel(String userId, String fullname, String email, String imoveis, Uri photoUser) {
        this.fullname = fullname;
        this.email = email;
        this.imoveis = imoveis;
        this.photoUser = photoUser;
        this.userId = userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImoveis() {
        return imoveis;
    }

    public void setImoveis(String imoveis) {
        this.imoveis = imoveis;
    }

    public Uri getPhotoUser() {
        return photoUser;
    }

    public void setPhotoUser(Uri photoUser) {
        this.photoUser = photoUser;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}