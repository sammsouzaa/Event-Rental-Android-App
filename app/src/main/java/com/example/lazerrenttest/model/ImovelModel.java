package com.example.lazerrenttest.model;

public class ImovelModel {

    private String status;

    private String typesofproperties,title, description, propertysize, price, capacity, beds, baths, street, streetnumber, neighborhood, city, ID_imoveis, idProprietario;

    private String nFavoritos, nLocations;
    public ImovelModel() {
    }

    public ImovelModel(String status, String typesofproperties, String title,String description, String propertysize, String price, String capacity,
                       String beds, String baths, String street, String streetnumber, String neighborhood, String city, String ID_imoveis, String idProprietario, String nFavoritos,String nLocations) {

        this.status = status;
        this.typesofproperties = typesofproperties;
        this.title = title;
        this.description = description;
        this.propertysize = propertysize;
        this.price = price;
        this.capacity = capacity;
        this.beds = beds;
        this.baths = baths;
        this.street = street;
        this.streetnumber = streetnumber;
        this.neighborhood = neighborhood;
        this.city = city;
        this.ID_imoveis = ID_imoveis;
        this.idProprietario = idProprietario;
        this.nFavoritos = nFavoritos;
        this.nLocations = nLocations;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPropertysize() {
        return propertysize;
    }

    public void setPropertysize(String propertysize) {
        this.propertysize = propertysize;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getBeds() {
        return beds;
    }

    public void setBeds(String beds) {
        this.beds = beds;
    }

    public String getBaths() {
        return baths;
    }

    public void setBaths(String baths) {
        this.baths = baths;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetnumber() {
        return streetnumber;
    }

    public void setStreetnumber(String streetnumber) {
        this.streetnumber = streetnumber;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getnFavoritos() {
        return nFavoritos;
    }

    public void setnFavoritos(String nFavoritos) {
        this.nFavoritos = nFavoritos;
    }

    public String getnLocations() {
        return nLocations;
    }

    public void setnLocations(String nLocations) {
        this.nLocations = nLocations;
    }
}