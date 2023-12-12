package com.example.lazerrenttest.ADM;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.lazerrenttest.R;
import com.example.lazerrenttest.adapter.ImoveisAdapter;
import com.example.lazerrenttest.databinding.ActivityAdmApprovalBinding;
import com.example.lazerrenttest.model.RecyclerImovelModel;
import com.example.lazerrenttest.sharedviewmodel.SharedViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import android.widget.Button;
import android.content.res.ColorStateList;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class adm_approval extends AppCompatActivity {

    private ActivityAdmApprovalBinding binding;
    private ProgressDialog dialog;

    private View view;

    private int novaCor;
    private int branco;

//    private SharedViewModel sharedViewModel;

//    NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdmApprovalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        novaCor = ContextCompat.getColor(adm_approval.this, R.color.corzinha);
//        branco = ContextCompat.getColor(adm_approval.this, R.color.white);
//        setOnClickListeners(view);

        Context context = getApplicationContext();

        binding.recyclerApproval.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

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

                            Log.d("testezinhao", "qnt: " + imoveis.size());

                            if (imoveis.size() == task.getResult().size()) {
                                List<RecyclerImovelModel> filteredImoveis = new ArrayList<>();
                                for (RecyclerImovelModel imovelItem : imoveis) {
                                    if ("pendente".equals(imovelItem.getStatus())) {
                                        filteredImoveis.add(imovelItem);
                                    }
                                }
                                ImoveisAdapter adapter = new ImoveisAdapter(filteredImoveis, context);
                                binding.recyclerApproval.setAdapter(adapter);
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

//    private void setOnClickListeners(View view) {
//
//        binding.pendentes.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onClick(View v) {
//
//                NavController navController = Navigation.findNavController(adm_approval.this, R.id.fragView);
//                NavDestination currentDestination = navController.getCurrentDestination();
//
//                if (currentDestination != null) {
//                    int currentDestinationId = currentDestination.getId();
//
//                    if (currentDestinationId == R.id.nav_pendentes) {
//
//                        Toast.makeText(adm_approval.this, "ja esta em pendentes", Toast.LENGTH_SHORT).show();
//
//                    }else{
//                        binding.pendentes.setTextColor(branco);
//                        binding.pendentes.setBackgroundTintList(ColorStateList.valueOf(novaCor));
//
//                        binding.notApprove.setTextColor(novaCor);
//                        binding.notApprove.setBackgroundTintList(null);
//                        navController.navigate(R.id.action_notAproved_to_pendentes);
//                    }
//                }
//            }
//        });
//        binding.notApprove.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onClick(View v) {
//
//                NavController navController = Navigation.findNavController(adm_approval.this, R.id.fragView);
//                NavDestination currentDestination = navController.getCurrentDestination();
//
//                if (currentDestination != null) {
//                    int currentDestinationId = currentDestination.getId();
//
//                    if (currentDestinationId == R.id.nav_notAproved) {
//
//                        Toast.makeText(adm_approval.this, "ja esta em not approved", Toast.LENGTH_SHORT).show();
//
//                    }else{
//                        binding.notApprove.setTextColor(branco);
//                        binding.notApprove.setBackgroundTintList(ColorStateList.valueOf(novaCor));
//
//                        binding.pendentes.setTextColor(novaCor);
//                        binding.pendentes.setBackgroundTintList(null);
//
//                        navController.navigate(R.id.action_pendentes_to_notAproved);
//                    }
//                }
//            }
//        });
//    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(adm_approval.this, ADM_dashboard.class));

    }
}