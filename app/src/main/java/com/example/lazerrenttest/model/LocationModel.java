package com.example.lazerrenttest.model;

import com.google.firebase.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LocationModel {
    private Timestamp data_entrada;
    private Timestamp data_saida;

    public LocationModel() {
    }

    public LocationModel(Calendar calendarDateEntry, Calendar calendarDateExit) {

        data_entrada = new Timestamp(calendarDateEntry.getTime());
        data_saida = new Timestamp(calendarDateExit.getTime());

    }

    public Timestamp getData_entrada() {
        return data_entrada;
    }

    public void setData_entrada(Timestamp data_entrada) {
        this.data_entrada = data_entrada;
    }

    public Timestamp getData_saida() {
        return data_saida;
    }

    public void setData_saida(Timestamp data_saida) {
        this.data_saida = data_saida;
    }

    public void getDataBaseInformations(){

    }
}
