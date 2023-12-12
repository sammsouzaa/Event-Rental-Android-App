package com.example.lazerrenttest.ADM;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lazerrenttest.ADM.information.information_imovel;
import com.example.lazerrenttest.R;
import com.example.lazerrenttest.adapter.ImoveisAdapter;
import com.example.lazerrenttest.databinding.ActivityAdmGeneralBinding;
import com.example.lazerrenttest.databinding.ActivityAdmPropertiesBinding;
import com.example.lazerrenttest.model.RecyclerImovelModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class adm_properties extends AppCompatActivity {

    ActivityAdmPropertiesBinding binding;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdmPropertiesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Context context = getApplicationContext();

        binding.recyclerProperties.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        dialog = new ProgressDialog(this);

        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        dialog.show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference imovRef = db.collection("imovel");

        imovRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().isEmpty()) {
                    dialog.dismiss();
                    Toast.makeText(context, "is empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    List<RecyclerImovelModel> imoveis = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        String typesofproperties = documentSnapshot.getString("typesofproperties");
                        String title = documentSnapshot.getString("title");
                        String ID_imoveis = documentSnapshot.getString("id_imoveis");
                        String idProprietario = documentSnapshot.getString("idProprietario");
                        String status = documentSnapshot.getString("status");

                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                        StorageReference imageRef = storageRef.child("images/imovel/" + ID_imoveis + "/Photo Principal");

                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            RecyclerImovelModel imovel = new RecyclerImovelModel(typesofproperties, title, ID_imoveis, idProprietario, uri, status);
                            imoveis.add(imovel);

                            if (imoveis.size() == task.getResult().size()) {
                                List<RecyclerImovelModel> filteredImoveis = new ArrayList<>();
                                for (RecyclerImovelModel imovelItem : imoveis) {
                                    if ("approved".equals(imovelItem.getStatus())) {
                                        filteredImoveis.add(imovelItem);
                                    }
                                }
                                ImoveisAdapter adapter = new ImoveisAdapter(filteredImoveis, context);
                                binding.recyclerProperties.setAdapter(adapter);
                                dialog.dismiss();
                            }
                        }).addOnFailureListener(e -> {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(adm_properties.this, ADM_dashboard.class));
    }
}