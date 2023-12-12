package com.example.lazerrenttest.model;

import android.net.Uri;

public class RecyclerImovelModel {

    private String typesofproperties, title, ID_imoveis, idProprietario;
    private String status;
    private Uri photoPrincipal;

    public RecyclerImovelModel(){

    }
    public RecyclerImovelModel(String typesofproperties, String title, String ID_imoveis,
                               String idProprietario, Uri photoPrincipal, String status) {

        this.typesofproperties = typesofproperties;
        this.title = title;
        this.ID_imoveis = ID_imoveis;
        this.idProprietario = idProprietario;
        this.status = status;

        this.photoPrincipal = photoPrincipal;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTypesofproperties() {
        return typesofproperties;
    }

    public void setTypesofproperties(String typesofproperties) {
        this.typesofproperties = typesofproperties;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getID_imoveis() {
        return ID_imoveis;
    }

    public void setID_imoveis(String ID_imoveis) {
        this.ID_imoveis = ID_imoveis;
    }

    public String getIdProprietario() {
        return idProprietario;
    }

    public void setIdProprietario(String idProprietario) {
        this.idProprietario = idProprietario;
    }

    public Uri getPhotoPrincipal() {
        return photoPrincipal;
    }

    public void setPhotoPrincipal(Uri photoPrincipal) {
        this.photoPrincipal = photoPrincipal;
    }

}
