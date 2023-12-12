package com.example.lazerrenttest.model;

public class CategoryModel {

    private String title, picPath, type;

    public CategoryModel() {
    }

    public CategoryModel(String title, String picPath, String type) {
        this.title = title;
        this.picPath = picPath;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
