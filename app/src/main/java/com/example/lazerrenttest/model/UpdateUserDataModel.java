package com.example.lazerrenttest.model;

public class UpdateUserDataModel {

    private String fullname, cpf, date, phone;

    public UpdateUserDataModel() {
    }

    public UpdateUserDataModel(String fullname, String cpf, String date, String phone) {
        this.fullname = fullname;
        this.cpf = cpf;
        this.date = date;
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public String getCpf() {
        return cpf;
    }

    public String getDate() {
        return date;
    }

    public String getPhone() {
        return phone;
    }
}
