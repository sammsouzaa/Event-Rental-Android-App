package com.example.lazerrenttest.Updates;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.lazerrenttest.activities.MainActivity;
import com.example.lazerrenttest.model.ImovelModel;
import com.example.lazerrenttest.model.ImovelPhotoModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UpdateImovelData {
    private Activity activity;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReferance;

    private ImovelModel imovelModel;
    private ImovelPhotoModel imovelPhotoModel;

    private String typeAntigo;

    public UpdateImovelData() {

    }

    public UpdateImovelData(Activity activity, ImovelModel imovelModel) {

        this.imovelModel = imovelModel;
        this.imovelPhotoModel = imovelPhotoModel;
        this.activity = activity;
        this.typeAntigo = typeAntigo;

        mAuth = mAuth.getInstance();
        firebaseFirestore = firebaseFirestore.getInstance();
        storageReferance = FirebaseStorage.getInstance().getReference();

        DocumentReference docRef = firebaseFirestore.collection("imovel").document(imovelModel.getID_imoveis());

        docRef.set(imovelModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(activity, "Sucessfully Update", Toast.LENGTH_SHORT).show();
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
