package com.example.lazerrenttest.sharedviewmodel;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<Uri> photoprincipal = new MutableLiveData<>();
    private MutableLiveData<Uri> photo1 = new MutableLiveData<>();
    private MutableLiveData<Uri> photo2 = new MutableLiveData<>();
    private MutableLiveData<Uri> photo3 = new MutableLiveData<>();
    private MutableLiveData<Uri> photo4 = new MutableLiveData<>();

    private MutableLiveData<String> typeproperty = new MutableLiveData<>();
    private MutableLiveData<String> title = new MutableLiveData<>();
    private MutableLiveData<String> description = new MutableLiveData<>();
    private MutableLiveData<String> propertysize = new MutableLiveData<>();
    private MutableLiveData<String> price = new MutableLiveData<>();
    private MutableLiveData<String> capacity = new MutableLiveData<>();
    private MutableLiveData<String> beds = new MutableLiveData<>();
    private MutableLiveData<String> baths = new MutableLiveData<>();
    private MutableLiveData<String> street = new MutableLiveData<>();
    private MutableLiveData<String> number = new MutableLiveData<>();
    private MutableLiveData<String> neighborhood = new MutableLiveData<>();
    private MutableLiveData<String> city = new MutableLiveData<>();
    private MutableLiveData<String> review = new MutableLiveData<>();

    /////////////////////////////////////////////////
    public void setPhotoprincipal(Uri photoprincipal) {

        Log.d("TESTE", "setPhotoprincipal");
        this.photoprincipal.setValue(photoprincipal);}
    public LiveData<Uri> getPhotoprincipal() {
        return photoprincipal;
    }

    /////////////////////////////////////////////////

    public void setPhoto1(Uri photo1) {
        Log.d("TESTE", "setPhoto1");
        this.photo1.setValue(photo1);
    }
    public LiveData<Uri> getPhoto1() {
        return photo1;
    }

    /////////////////////////////////////////////////

    public void setPhoto2(Uri photo2) {
        Log.d("TESTE", "setPhoto2");
        this.photo2.setValue(photo2);
    }
    public LiveData<Uri> getPhoto2() {
        return photo2;
    }

    /////////////////////////////////////////////////
    public void setPhoto3(Uri photo3) {
        Log.d("TESTE", "setPhoto3");
        this.photo3.setValue(photo3);
    }

    public LiveData<Uri> getPhoto3() {
        return photo3;
    }

    /////////////////////////////////////////////////
    public void setPhoto4(Uri photo4) {
        Log.d("TESTE", "setPhoto4");
        this.photo4.setValue(photo4);
    }

    public LiveData<Uri> getPhoto4() {
        return photo4;
    }

    /////////////////////////////////////////////////
    public void setTypeproperty(String typeproperty) {
        this.typeproperty.setValue(typeproperty);
    }
    public LiveData<String> getTypeproperty() {
        return typeproperty;
    }

    /////////////////////////////////////////////////

    public void setTitle(String title) {
        this.title.setValue(title);
    }
    public LiveData<String> getTitle() {
        return title;
    }

    /////////////////////////////////////////////////
    public void setDescription(String description) {
        this.description.setValue(description);
    }

    public LiveData<String> getDescription() {
        return description;
    }

    /////////////////////////////////////////////////
    public void setPropertysize(String propertysize) {
        this.propertysize.setValue(propertysize);
    }

    public LiveData<String> getPropertysize() {
        return propertysize;
    }

    /////////////////////////////////////////////////
    /////////////////////////////////////////////////

    public void setPrice(String price) {
        this.price.setValue(price);
    }

    public LiveData<String> getPrice() {
        return price;
    }
    /////////////////////////////////////////////////

    public void setCapacity(String capacity) {
        this.capacity.setValue(capacity);
    }
    public LiveData<String> getCapacity() {
        return capacity;
    }

    /////////////////////////////////////////////////
    public void setBeds(String beds) {
        this.beds.setValue(beds);
    }

    public LiveData<String> getBeds() {
        return beds;
    }

    /////////////////////////////////////////////////
    public void setBaths(String baths) {
        this.baths.setValue(baths);
    }

    public LiveData<String> getBaths() {
        return baths;
    }

    /////////////////////////////////////////////////
    public void setStreet(String street) {
        this.street.setValue(street);
    }
    public LiveData<String> getStreet() {
        return street;
    }

    /////////////////////////////////////////////////

    public void setNumber(String number) {
        this.number.setValue(number);
    }
    public LiveData<String> getNumber() {
        return number;
    }

    /////////////////////////////////////////////////
    public void setNeighborhood(String neighborhood) {
        this.neighborhood.setValue(neighborhood);
    }

    public LiveData<String> getNeighborhood() {
        return neighborhood;
    }

    /////////////////////////////////////////////////
    public void setCity(String city) {
        this.city.setValue(city);
    }

    public LiveData<String> getCity() {
        return city;
    }

    /////////////////////////////////////////////////
    public void setReview(String review) {
        this.review.setValue(review);
    }

    public LiveData<String> getReview() {
        return review;
    }

    /////////////////////////////////////////////////



}