package com.example.lazerrenttest.registers;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.lazerrenttest.activities.MainActivity;
import com.example.lazerrenttest.model.LocationModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.UUID;

public class RegisterLocation {
    private LocationModel locationModel;
    private Activity activity;
    private String IDimovel;
    private String IDusuarioCurrent;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    public RegisterLocation() {
    }

    public RegisterLocation(LocationModel locationModel, Activity activity, String IDimovel, String IDusuarioCurrent) {
        this.locationModel = locationModel;
        this.activity = activity;
        this.IDimovel = IDimovel;
        this.IDusuarioCurrent = IDusuarioCurrent;

        mAuth = mAuth.getInstance();
        firebaseFirestore = firebaseFirestore.getInstance();

        firebaseFirestore.collection("imovel")
                .document(IDimovel).collection("locations").document(UUID.randomUUID().toString())
                .set(locationModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(activity, "Sucessfull Location!!", Toast.LENGTH_SHORT).show();
                        activity.startActivity(new Intent(activity, MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
