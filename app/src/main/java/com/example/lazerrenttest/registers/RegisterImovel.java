package com.example.lazerrenttest.registers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.example.lazerrenttest.R;
import com.example.lazerrenttest.fragmentsMyLocation.ViewMyLocationsFragment;
import com.example.lazerrenttest.model.IdImovelModel;
import com.example.lazerrenttest.model.ImovelModel;
import com.example.lazerrenttest.model.ImovelPhotoModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

//        Log.d("TESTE", "Valor Antes de tudo = " + n_Imoveis);

public class RegisterImovel {
    public RegisterImovel() {
    }
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    private Activity activity;

    private String usuarioID;

    View view;

    private IdImovelModel idImovelModel = new IdImovelModel();
    private ImovelModel imovelModel = new ImovelModel();
    private ImovelPhotoModel imovelPhotoModel = new ImovelPhotoModel();

    private int n_Imoveis = 0;

    private StorageReference storageReferance;

    public RegisterImovel(Activity activity, ImovelModel imovelModel, IdImovelModel idImovelModel,ImovelPhotoModel imovelPhotoModel) {

        this.imovelModel = imovelModel;
        this.idImovelModel = idImovelModel;
        this.imovelPhotoModel = imovelPhotoModel;
        this.activity = activity;

        mAuth = mAuth.getInstance();
        firebaseFirestore = firebaseFirestore.getInstance();
        storageReferance = FirebaseStorage.getInstance().getReference();

        firebaseFirestore.collection("imovel")
                                .document(imovelModel.getID_imoveis()).set(imovelModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        DocumentReference documentReference = firebaseFirestore.collection("user").document(usuarioID);

                        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                if (task.isSuccessful()) {
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    if (documentSnapshot != null && documentSnapshot.exists()) {
                                        int imoveis = Integer.parseInt(documentSnapshot.getString("imoveis")) + 1;

                                        firebaseFirestore.collection("user").document(mAuth.getUid()).collection("imoveis")
                                                .document(idImovelModel.getId_imovel()).set(idImovelModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {

                                                        documentReference.update("imoveis", Integer.toString(imoveis))
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {

                                                                        Map<Uri, String> imageUriToFileName = new HashMap<>();
                                                                        imageUriToFileName.put(imovelPhotoModel.getPhotoPrincipal(), "Photo Principal");
                                                                        imageUriToFileName.put(imovelPhotoModel.getPhoto1(), "Photo 1");
                                                                        imageUriToFileName.put(imovelPhotoModel.getPhoto2(), "Photo 2");
                                                                        imageUriToFileName.put(imovelPhotoModel.getPhoto3(), "Photo 3");
                                                                        imageUriToFileName.put(imovelPhotoModel.getPhoto4(), "Photo 4");

                                                                        String id = imovelModel.getID_imoveis();

                                                                        for (Map.Entry<Uri, String> entry : imageUriToFileName.entrySet()) {
                                                                            Uri uri = entry.getKey();
                                                                            String fileName = entry.getValue();

                                                                            String path = "images/imovel/" + id + "/" + fileName;

                                                                            StorageReference imageRef = storageReferance.child(path);

                                                                            imageRef.putFile(uri);
                                                                        }

                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}