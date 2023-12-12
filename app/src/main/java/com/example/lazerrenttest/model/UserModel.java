package com.example.lazerrenttest.model;

public class UserModel {
    private String fullname, username, email, phone, cpf, imoveis, date;

    public UserModel() {
    }

    public UserModel(String fullname, String username, String email, String phone, String cpf, String imoveis, String date) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.cpf = cpf;
        this.imoveis = imoveis;
        this.date = date;
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


    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImoveis() {
        return imoveis;
    }

    public void setImoveis(String imoveis) {
        this.imoveis = imoveis;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}