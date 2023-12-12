package com.example.lazerrenttest.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lazerrenttest.adapter.RecentsAdapter;
import com.example.lazerrenttest.customDialog.Dialog_filter;
import com.example.lazerrenttest.databinding.ActyHomeSearchBinding;
import com.example.lazerrenttest.model.RecentsData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ActyHomeSearch extends AppCompatActivity {

    //declarando variaveis a ser utilizado na classe
    private ActyHomeSearchBinding binding;
    private Dialog_filter dialogFilter;
    private ProgressDialog dialog;
    private RecentsAdapter adapter;
    private List<RecentsData> filteredImoveis;
    private List<RecentsData> filteredImoveis2 = new ArrayList<>();
    private String typefilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActyHomeSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Recebendo o Intent que foi usado pela activity anterior e atribuindo o valor desse intent e atribuindo a variavel typeFilter;

        Intent intent = getIntent();
        typefilter = intent.getStringExtra("type");

        //inicializando a dialog de filtro;
        dialogFilter = new Dialog_filter(ActyHomeSearch.this, ActyHomeSearch.this);

        //inicializando o arraylist a ser usado no recyclerview;
        filteredImoveis = new ArrayList<>();
        binding.recyclerSearch.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //criando uma dialog de progresso que vai ser exibida enquando o recycler é populado;
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();

        //criando uma referencia com o banco de dados para receber as informações;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference imovRef = db.collection("imovel");

        imovRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().isEmpty()) {
                    dialog.dismiss();
                    Toast.makeText(this, "is empty", Toast.LENGTH_SHORT).show();
                } else {
                    List<RecentsData> imoveis = new ArrayList<>();

                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        String typesofproperties = documentSnapshot.getString("typesofproperties");
                        String title = documentSnapshot.getString("title");
                        String ID_imoveis = documentSnapshot.getString("id_imoveis");
                        String status = documentSnapshot.getString("status");
                        String type = documentSnapshot.getString("typesofproperties");

                        String address = documentSnapshot.getString("street") + ", " + documentSnapshot.getString("streetnumber");
                        String price = documentSnapshot.getString("price");

                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                        StorageReference imageRef = storageRef.child("images/imovel/" + ID_imoveis + "/Photo Principal");

                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            RecentsData imovel = new RecentsData(title, address, price, ID_imoveis, status, type, uri);
                            imoveis.add(imovel);

                            if (imoveis.size() == task.getResult().size()) {
                                for (RecentsData imovelItem : imoveis) {
                                    if ("approved".equals(imovelItem.getStatus())) {
                                        filteredImoveis.add(imovelItem);
                                    }
                                }
                                if (!typefilter.equals("nada")) {
                                    for (RecentsData imovelItem : filteredImoveis) {
                                        if (typefilter.equals(imovelItem.getType())) {
                                            filteredImoveis2.add(imovelItem);
                                        }
                                    }
                                    if (filteredImoveis2.isEmpty()) {
                                        Toast.makeText(this, "nothing", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } else {
                                        adapter = new RecentsAdapter(ActyHomeSearch.this, filteredImoveis2);
                                        binding.recyclerSearch.setAdapter(adapter);
                                        dialog.dismiss();
                                    }
                                } else {
                                    adapter = new RecentsAdapter(ActyHomeSearch.this, filteredImoveis);
                                    binding.recyclerSearch.setAdapter(adapter);
                                    dialog.dismiss();
                                }
                            }
                        }).addOnFailureListener(e -> {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        });
                    }
                }
            }
        });

        binding.filter.setOnClickListener(view -> exibirDialogFiltro());

        //recebendo o tipo de filtro a ser usado;
        binding.searchFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (dialogFilter.getFilter().equals("title")) {
                    filterTitle(editable.toString());
                } else if (dialogFilter.getFilter().equals("address")) {
                    filterAddress(editable.toString());
                }
            }
        });
    }

    //configurando o filtro dos imoveis que vao popular o recyclerview;

    //filtro por titulo;
    private void filterTitle(String text) {
        List<RecentsData> filter = new ArrayList<>();

        if (typefilter != null && !typefilter.equals("nada")) {
            for (RecentsData item : filteredImoveis2) {
                if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                    filter.add(item);
                }
            }
        } else {
            for (RecentsData item : filteredImoveis) {
                if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                    filter.add(item);
                }
            }
        }
        adapter.filterList(filter);
    }

    //filtro por endereço;
    private void filterAddress(String text) {
        List<RecentsData> filter = new ArrayList<>();

        if (typefilter != null && !typefilter.equals("nada")) {
            for (RecentsData item : filteredImoveis2) {
                if (item.getAddress().toLowerCase().contains(text.toLowerCase())) {
                    filter.add(item);
                }
            }
        } else {
            for (RecentsData item : filteredImoveis) {
                if (item.getAddress().toLowerCase().contains(text.toLowerCase())) {
                    filter.add(item);
                }
            }
        }
        adapter.filterList(filter);
    }

    //metodo criado para exibir a dialog que escolhe o tipo da filtragem;
    private void exibirDialogFiltro() {
        dialogFilter.setCancelable(false);
        dialogFilter.show();
    }

    // ao voltar, inicia um intent e vai para a activity main (principal);
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ActyHomeSearch.this, MainActivity.class));
    }
}