package com.example.lazerrenttest.ADM;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lazerrenttest.ADM.information.information_imovel;
import com.example.lazerrenttest.R;
import com.example.lazerrenttest.adapter.UsersAdapter;
import com.example.lazerrenttest.databinding.ActivityAdmSupportBinding;
import com.example.lazerrenttest.databinding.ActivityAdmUsersBinding;
import com.example.lazerrenttest.model.RecyclerUserModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class adm_users extends AppCompatActivity {
    ActivityAdmUsersBinding binding;
    List<RecyclerUserModel> usuarios;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdmUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();

        Context context = getApplicationContext();

        binding.recyclerUsers.setLayoutManager(new LinearLayoutManager(this));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("user");

        usersRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().isEmpty()) {

                    dialog.dismiss();

                } else {
                    usersRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
                        usuarios = new ArrayList<>();

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String nome = documentSnapshot.getString("fullname");
                            String email = documentSnapshot.getString("email");
                            String imoveis = documentSnapshot.getString("imoveis");
                            String userId = documentSnapshot.getId();

                            // Recuperar a URL da imagem do perfil do usuário a partir do Storage
                            String imagePath = "images/profile/" + userId + "/profilephoto";
                            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                            StorageReference imageRef = storageRef.child(imagePath);

                            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                Uri photo = uri;

                                // Crie um novo objeto User com todos os dados, incluindo a URL da imagem
                                RecyclerUserModel user = new RecyclerUserModel(userId, nome, email, imoveis, photo);
                                usuarios.add(user);

                                // Verifique se todos os usuários foram processados e a lista está completa
                                if (usuarios.size() == queryDocumentSnapshots.size()) {
                                    // Agora a lista de usuários está pronta com todas as informações necessárias
                                    // Passe essa lista para o seu adapter e atualize o RecyclerView
                                    UsersAdapter adapter = new UsersAdapter(usuarios, context);
                                    binding.recyclerUsers.setAdapter(adapter);
                                    dialog.dismiss();
                                }
                            }).addOnFailureListener(e -> {

                                // Crie um novo objeto User com todos os dados, incluindo a URL da imagem
                                RecyclerUserModel user = new RecyclerUserModel(userId, nome, email, imoveis, null);
                                usuarios.add(user);

                                // Verifique se todos os usuários foram processados e a lista está completa
                                if (usuarios.size() == queryDocumentSnapshots.size()) {
                                    // Agora a lista de usuários está pronta com todas as informações necessárias
                                    // Passe essa lista para o seu adapter e atualize o RecyclerView
                                    UsersAdapter adapter = new UsersAdapter(usuarios, context);
                                    binding.recyclerUsers.setAdapter(adapter);
                                    dialog.dismiss();
                                }
                            });
                        }
                    }).addOnFailureListener(e -> {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            } else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {

        startActivity(new Intent(adm_users.this, ADM_dashboard.class));

    }
}