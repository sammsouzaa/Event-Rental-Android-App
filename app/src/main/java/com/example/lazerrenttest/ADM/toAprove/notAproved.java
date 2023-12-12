package com.example.lazerrenttest.ADM.toAprove;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lazerrenttest.R;
import com.example.lazerrenttest.adapter.ImoveisAdapter;
import com.example.lazerrenttest.databinding.FragmentNotAprovedBinding;
import com.example.lazerrenttest.databinding.FragmentPendentesBinding;
import com.example.lazerrenttest.model.RecyclerImovelModel;
import com.example.lazerrenttest.sharedviewmodel.SharedViewModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class notAproved extends Fragment {


    public notAproved() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentNotAprovedBinding binding;

//    SharedViewModel sharedViewModel;
    ProgressDialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotAprovedBinding.inflate(inflater, container, false);
//        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerApproval2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        dialog = new ProgressDialog(getContext());

        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        dialog.show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference imovRef = db.collection("imovel");

        imovRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                if (task.getResult().isEmpty()) {
                    dialog.dismiss(); // Fechar a dialog se a lista de imóveis estiver vazia
                    Toast.makeText(getContext(), "is empty", Toast.LENGTH_SHORT).show();
                } else {

                    List<RecyclerImovelModel> imoveis = new ArrayList<>();

                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                        Log.d("pendentes", "entrou no for");

                        String typesofproperties = documentSnapshot.getString("typesofproperties");
                        String title = documentSnapshot.getString("title");
                        String ID_imoveis = documentSnapshot.getString("id_imoveis");
                        String idProprietario = documentSnapshot.getString("idProprietario");
                        String status = documentSnapshot.getString("status");

                        Log.d("pendentes", "pegou dados" + ID_imoveis);

                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                        StorageReference imageRef = storageRef.child("images/imovel/" + typesofproperties + "/" + ID_imoveis + "/Photo Principal");

                        Log.d("pendentes", "storage references" + ID_imoveis);

                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {

                            RecyclerImovelModel imovel = new RecyclerImovelModel(typesofproperties, title, ID_imoveis, idProprietario, uri, status);
                            imoveis.add(imovel);

                            Log.d("pendentes", "add imovel");

                            if (imoveis.size() == task.getResult().size()) {
                                List<RecyclerImovelModel> filteredImoveis = new ArrayList<>();
                                for (RecyclerImovelModel imovelItem : imoveis) {
                                    if ("not approved".equals(imovelItem.getStatus())) {
                                        filteredImoveis.add(imovelItem);
                                    }
                                }
                                ImoveisAdapter adapter = new ImoveisAdapter(filteredImoveis, getContext());
                                Log.d("pendentes", "criou adapter");
                                binding.recyclerApproval2.setAdapter(adapter);
                                Log.d("pendentes", "setou adapter");
                                dialog.dismiss();
                                Log.d("pendentes", "fechou dialog");
                            }
                        }).addOnFailureListener(e -> {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }
                }

            }
        });
    }
}