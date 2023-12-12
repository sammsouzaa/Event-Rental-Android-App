package com.example.lazerrenttest.Updates;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.lazerrenttest.model.UpdateUserDataModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateUserData {

    private String IDusuario;
    private UpdateUserDataModel updateUserDataModel;
    private Activity activity;

    public UpdateUserData() {
    }

    public UpdateUserData(Activity activity, String IDusuario, UpdateUserDataModel updateUserDataModel) {

        this.activity = activity;
        this.IDusuario = IDusuario;
        this.updateUserDataModel = updateUserDataModel;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("user").document(IDusuario);

        Map<String, Object> updates = new HashMap<>();
        updates.put("fullname", updateUserDataModel.getFullname());
        updates.put("date", updateUserDataModel.getDate());
        updates.put("phone", updateUserDataModel.getPhone());
        updates.put("cpf", updateUserDataModel.getCpf());

        docRef.update(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(activity, "Sucessfull User Update ", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
