package com.example.lazerrenttest.Database;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LocationsData {

    private String IDimovel;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firebaseFirestore;

    private List<Date> datas = new ArrayList<>();
    private Activity activity;

    public LocationsData() {
    }

    public LocationsData(Activity activity, String IDimovel, final OnDataFetchComplete listener) {
        this.IDimovel = IDimovel;
        this.activity = activity;
        firebaseFirestore = FirebaseFirestore.getInstance();
        Log.d("LocationsData", "ID: " + IDimovel);

        CollectionReference documentReference = firebaseFirestore.collection("imovel").document(IDimovel)
                .collection("locations");

        documentReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("LocationsData", "entrou no for");

                                Timestamp timestampEntrada = document.getTimestamp("data_entrada");
                                Timestamp timestampSaida = document.getTimestamp("data_saida");
                                Log.d("LocationsData", "pegou os timestamp");

                                if (timestampEntrada != null && timestampSaida != null) {
                                    Calendar calendarEntrada = Calendar.getInstance();
                                    calendarEntrada.setTimeInMillis(timestampEntrada.getSeconds() * 1000); // Converte segundos para milissegundos
                                    calendarEntrada.set(Calendar.HOUR_OF_DAY, 0); // Define a hora para 00:00:00
                                    calendarEntrada.set(Calendar.MINUTE, 0);
                                    calendarEntrada.set(Calendar.SECOND, 0);
                                    calendarEntrada.set(Calendar.MILLISECOND, 0);

                                    Calendar calendarSaida = Calendar.getInstance();
                                    calendarSaida.setTimeInMillis(timestampSaida.getSeconds() * 1000); // Converte segundos para milissegundos
                                    calendarSaida.set(Calendar.HOUR_OF_DAY, 0); // Define a hora para 00:00:00
                                    calendarSaida.set(Calendar.MINUTE, 0);
                                    calendarSaida.set(Calendar.SECOND, 0);
                                    calendarSaida.set(Calendar.MILLISECOND, 0);

                                    Date dataEntrada = calendarEntrada.getTime();
                                    Date dataSaida = calendarSaida.getTime();

//                                    Toast.makeText(activity, dataEntrada.toString(), Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(activity, dataSaida.toString(), Toast.LENGTH_SHORT).show();

                                    getDatesBetween(dataEntrada, dataSaida);
                                }
                            }
                            listener.onDataFetchComplete(datas);
                            Log.d("LocationsData", "");
                        } else {
                            Log.d("TAG", "Erro ao obter documentos", task.getException());
                        }
                    }
                });
    }
    public interface OnDataFetchComplete {
        void onDataFetchComplete(List<Date> datas);
    }
    public void getDatesBetween(Date dataEntrada, Date dataSaida) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataEntrada);

        while (!calendar.getTime().after(dataSaida)) {
            datas.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
    public List<Date> getDatas() {
        return datas;
    }
}
