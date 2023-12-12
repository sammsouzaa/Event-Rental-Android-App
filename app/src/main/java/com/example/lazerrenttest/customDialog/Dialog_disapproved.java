package com.example.lazerrenttest.customDialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.lazerrenttest.ADM.ADM_dashboard;
import com.example.lazerrenttest.ADM.information.information_imovel;
import com.example.lazerrenttest.R;
import com.example.lazerrenttest.model.NotificationsModel;
import com.example.lazerrenttest.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class Dialog_disapproved extends Dialog {

    private Context context;
    private String idProprietario;
    private String idImovel;

    private Context parentContext;

    public Dialog_disapproved(@NonNull Context context, String idProprietario, String idImovel) {
        super(context);

        this.idProprietario = idProprietario;
        this.context = context;
        this.idImovel = idImovel;
        this.parentContext = context;

    }
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button btn_send;
    private EditText desc;
    private FirebaseFirestore firebaseFirestore;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(android.R.color.transparent)));
        setContentView(R.layout.dialog_disapproved);

        btn_send = findViewById(R.id.button_send);
        desc = findViewById(R.id.edittext_add_description);
        firebaseFirestore = firebaseFirestore.getInstance();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DocumentReference documentRef = db.collection("imovel").document(idImovel);

                documentRef
                        .update("status", "disapproved")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                DocumentReference documentReference = firebaseFirestore.collection("user").document(idProprietario);

                                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                        if (task.isSuccessful()) {
                                            DocumentSnapshot documentSnapshot = task.getResult();
                                            if (documentSnapshot != null && documentSnapshot.exists()) {

                                                NotificationsModel notificationsModel = new NotificationsModel("Property disapproved", desc.getText().toString(), "imovel");

                                                firebaseFirestore.collection("user").document(idProprietario).collection("notifications")
                                                        .document(UUID.randomUUID().toString()).set(notificationsModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {

                                                                parentContext.startActivity(new Intent(context, ADM_dashboard.class));
                                                                Toast.makeText(context, "Successfully disapproved!!", Toast.LENGTH_SHORT).show();
                                                                dismiss();
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}
