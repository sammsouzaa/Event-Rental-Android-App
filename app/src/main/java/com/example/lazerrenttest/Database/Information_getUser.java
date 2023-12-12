package com.example.lazerrenttest.Database;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Information_getUser {

    private String fullname, username, email, phone, cpf, imoveis, date;
    private Uri photoPerfil;

    private Context context;
    private String ID;

    public Information_getUser(Context context, String ID) {
        this.ID = ID;
        this.context = context;
    }

    public void fetchUserInfo() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("user").document(ID);

        userRef.get().addOnSuccessListener(this::onUserInfoSuccess)
                .addOnFailureListener(this::onUserInfoFailure);
    }

    private void onUserInfoSuccess(DocumentSnapshot documentSnapshot) {
        if (documentSnapshot.exists()) {
            fullname = documentSnapshot.getString("fullname");
            username = documentSnapshot.getString("username");
            email = documentSnapshot.getString("email");
            phone = documentSnapshot.getString("phone");
            cpf = documentSnapshot.getString("cpf");
            imoveis = documentSnapshot.getString("imoveis");
            date = documentSnapshot.getString("date");

            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference imageRef = storageRef.child("images/profile/" + ID + "/profilephoto");

            imageRef.getDownloadUrl().addOnSuccessListener(this::onPhotoDownloadSuccess)
                    .addOnFailureListener(this::onPhotoDownloadFailure);
        } else {
            showToast("Usuário não encontrado");
        }
    }

    private void onUserInfoFailure(@NonNull Exception e) {
        showToast("Erro ao obter informações do usuário: " + e.getMessage());
    }

    private void onPhotoDownloadSuccess(Uri uri) {
        photoPerfil = uri;
        // Aqui você pode chamar um método ou notificar a UI de que as informações foram carregadas
        // Exemplo: informar que as informações do usuário foram carregadas
    }

    private void onPhotoDownloadFailure(@NonNull Exception e) {
        showToast("Erro ao obter a foto do perfil: " + e.getMessage());
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public String getFullname() {
        return fullname;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCpf() {
        return cpf;
    }

    public String getImoveis() {
        return imoveis;
    }

    public String getDate() {
        return date;
    }

    public Uri getPhotoPerfil() {
        return photoPerfil;
    }
}
