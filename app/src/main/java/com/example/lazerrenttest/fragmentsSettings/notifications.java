package com.example.lazerrenttest.fragmentsSettings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lazerrenttest.activities.MainActivity;
import com.example.lazerrenttest.adapter.NotificationsAdapter;
import com.example.lazerrenttest.databinding.ActySettingsNotificationsBinding;
import com.example.lazerrenttest.model.NotificationsModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class notifications extends AppCompatActivity {

    ActySettingsNotificationsBinding binding;

    ProgressDialog dialog;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActySettingsNotificationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new ProgressDialog(this);

        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        dialog.show();

        binding.notificationRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("user").document(userId).collection("notifications")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<NotificationsModel> Notifications = new ArrayList<>();

                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String title = documentSnapshot.getString("title");
                            String desc = documentSnapshot.getString("desc");
                            String type = documentSnapshot.getString("type");

                            Notifications.add(new NotificationsModel(title, desc, type));

                            if (Notifications.size() == queryDocumentSnapshots.size()){
                                NotificationsAdapter adapter = new NotificationsAdapter(notifications.this, Notifications);
                                binding.notificationRecycler.setAdapter(adapter);
                                dialog.dismiss();
                            }

                        }

                    }
                });

        binding.closebutao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(notifications.this, MainActivity.class));
            }
        });

        binding.closebutao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}