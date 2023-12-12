package com.example.lazerrenttest.registers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.lazerrenttest.authentication.Login;
import com.example.lazerrenttest.model.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateUser {
    private String email,password;

    public CreateUser() {
    }

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    UserModel dataUser = new UserModel();

    Context context;

    private ProgressDialog progressDialog;


    public CreateUser(Context context, UserModel dataUser, String password) {

        this.dataUser = dataUser;

        this.email = dataUser.getEmail();
        this.password = password;

        this.context = context;

        mAuth = mAuth.getInstance();
        firebaseFirestore = firebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(context);

        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        progressDialog.cancel();
                        context.startActivity(new Intent(context, Login.class));
                        Toast.makeText(context, "Register Succesfull", Toast.LENGTH_SHORT).show();

                        firebaseFirestore.collection("user")
                                .document(mAuth.getUid()).set(dataUser);

//                firebaseFirestore.collection("user").document(mAuth.getUid()).collection("userdata").document().set(new UserModel(name, email, phone, cpf));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.cancel();
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
