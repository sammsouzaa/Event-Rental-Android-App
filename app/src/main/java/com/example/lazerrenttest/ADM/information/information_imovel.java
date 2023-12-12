package com.example.lazerrenttest.ADM.information;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lazerrenttest.ADM.ADM_dashboard;
import com.example.lazerrenttest.Database.Information_getImovel;
import com.example.lazerrenttest.customDialog.Dialog_disapproved;
import com.example.lazerrenttest.databinding.ActivityInformationImovelBinding;
import com.example.lazerrenttest.model.NotificationsModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import android.widget.LinearLayout.LayoutParams;

import java.util.UUID;

public class information_imovel extends AppCompatActivity {

    private ActivityInformationImovelBinding binding;
    private String IDimovel;
    private Information_getImovel information_getImovel;
    private ProgressDialog dialog;
    private LayoutParams params;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInformationImovelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new ProgressDialog(this);

        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        dialog.show();

        params = (LayoutParams) binding.btnNotApprove.getLayoutParams();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            IDimovel = bundle.getString("ID_do_imovel");
        }
        information_getImovel = new Information_getImovel(this, IDimovel);

        information_getImovel.fetchImovelInfo(new Information_getImovel.ImovelInfoCallback() {
            @Override
            public void onImovelInfoLoaded() {

                loadData();
                setOnClickListeners();
            }

            @Override
            public void onImovelInfoFailed(Exception e) {

                dialog.dismiss();
                Toast.makeText(information_imovel.this, "Erro ao carregar informações do imóvel", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setOnClickListeners() {
        binding.btnBackprevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(information_imovel.this, ADM_dashboard.class));
            }
        });
        binding.btnNotApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog_disapproved dialog_disapproved = new Dialog_disapproved(information_imovel.this, information_getImovel.getIdProprietario(), information_getImovel.getID_imoveis());
                dialog_disapproved.setCancelable(false);
                dialog_disapproved.show();

//                DocumentReference documentRef = db.collection("imovel").document(IDimovel);
//
//                documentRef
//                        .update("status", "not approved")
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                dialog.dismiss();
//                                startActivity(new Intent(information_imovel.this, ADM_dashboard.class));
//                                Toast.makeText(information_imovel.this, "Successfully approved!!", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                dialog.dismiss();
//                                Toast.makeText(information_imovel.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
            }
        });

        binding.btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();

                DocumentReference documentRef = db.collection("imovel").document(IDimovel);

                documentRef
                        .update("status", "approved")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                NotificationsModel notificationsModel = new NotificationsModel("Property approved", "Congratulations!, your property is ready for locations", "imovel");

                                db.collection("user").document(information_getImovel.getIdProprietario()).collection("notifications")
                                        .document(UUID.randomUUID().toString()).set(notificationsModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                                dialog.dismiss();
                                                startActivity(new Intent(information_imovel.this, ADM_dashboard.class));
                                                Toast.makeText(information_imovel.this, "Successfully approved!!", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.dismiss();
                                Toast.makeText(information_imovel.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void loadData() {

        if(information_getImovel.getStatus().equals("approved")){
            binding.btnApprove.setVisibility(View.GONE);
        }else if(information_getImovel.getStatus().equals("not approved")){
            binding.btnNotApprove.setVisibility(View.GONE);
            params.setMarginStart(0);
        }

        binding.infTypeofproperty.setText(information_getImovel.getTypesofproperties());
        binding.infTitle.setText(information_getImovel.getTitle());
        binding.infDescription.setText(information_getImovel.getDescription());
        binding.infPropertySize.setText(information_getImovel.getPropertysize() + "m2");
        binding.infPrice.setText("r$ " + information_getImovel.getPrice() + " /day");
        binding.infCapacity.setText("Capacity: " + information_getImovel.getCapacity() + " people");
        binding.infBeds.setText(information_getImovel.getBeds() + " beds");
        binding.infBaths.setText(information_getImovel.getBaths() + " baths");
        binding.infStreet.setText(information_getImovel.getStreet());
        binding.infStreetNumber.setText(information_getImovel.getStreetnumber());
        binding.infNeighborhood.setText(information_getImovel.getNeighborhood());
        binding.infCity.setText(information_getImovel.getCity());

        Glide.with(this).load(information_getImovel.getPhotoPrincipal()).into(binding.infPhotoPrincipal);
        Glide.with(this).load(information_getImovel.getPhoto1()).into(binding.infPhoto1);
        Glide.with(this).load(information_getImovel.getPhoto2()).into(binding.infPhoto2);
        Glide.with(this).load(information_getImovel.getPhoto3()).into(binding.infPhoto3);
        Glide.with(this).load(information_getImovel.getPhoto4()).into(binding.infPhoto4);

        dialog.dismiss();
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(information_imovel.this, ADM_dashboard.class));

    }
}