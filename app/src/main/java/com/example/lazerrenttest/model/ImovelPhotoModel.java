package com.example.lazerrenttest.model;

import android.net.Uri;

public class ImovelPhotoModel {

    private Uri photoPrincipal, photo1, photo2, photo3, photo4;

    public ImovelPhotoModel() {
    }

    public ImovelPhotoModel(Uri photoPrincipal, Uri photo1, Uri photo2, Uri photo3, Uri photo4) {
        this.photoPrincipal = photoPrincipal;
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.photo3 = photo3;
        this.photo4 = photo4;
    }

    public Uri getPhotoPrincipal() {
        return photoPrincipal;
    }

    public void setPhotoPrincipal(Uri photoPrincipal) {
        this.photoPrincipal = photoPrincipal;
    }

    public Uri getPhoto1() {
        return photo1;
    }

    public void setPhoto1(Uri photo1) {
        this.photo1 = photo1;
    }

    public Uri getPhoto2() {
        return photo2;
    }

    public void setPhoto2(Uri photo2) {
        this.photo2 = photo2;
    }

    public Uri getPhoto3() {
        return photo3;
    }

    public void setPhoto3(Uri photo3) {
        this.photo3 = photo3;
    }

    public Uri getPhoto4() {
        return photo4;
    }

    public void setPhoto4(Uri photo4) {
        this.photo4 = photo4;
    }
}
