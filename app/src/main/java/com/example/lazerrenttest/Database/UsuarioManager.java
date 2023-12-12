package com.example.lazerrenttest.Database;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.Task;

public class UsuarioManager {

    private FirebaseFirestore firebaseFirestore;

    public UsuarioManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public Task<DocumentSnapshot> getUsuario(String userID) {

        String usuarioID = userID;
        DocumentReference documentReference = firebaseFirestore.collection("user").document(usuarioID);

        return documentReference.get();
    }

}