package com.example.lazerrenttest.fragmentsMenu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.lazerrenttest.activities.ProfileUser;
import com.example.lazerrenttest.authentication.Login;
import com.example.lazerrenttest.databinding.FragmentSettingsBinding;
import com.example.lazerrenttest.fragmentsSettings.contactUs;
import com.example.lazerrenttest.fragmentsSettings.editProfile;
import com.example.lazerrenttest.fragmentsSettings.myLocations;
import com.example.lazerrenttest.fragmentsSettings.notifications;
import com.example.lazerrenttest.fragmentsSettings.settings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
//
//import com.example.lazerrenttest.settings.settings.*;

public class SettingsFragment extends Fragment {

    public SettingsFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

    FragmentSettingsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    String usuarioID;
    FirebaseFirestore mFire = FirebaseFirestore.getInstance();

    Activity context;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();
        readUserdata();
        onClickLineters();

        binding.goToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ProfileUser.class));
            }
        });
    }

    private void onClickLineters() {

        binding.btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                auth.signOut();
                startActivity(new Intent(context, Login.class));
            }
        });

        binding.btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, editProfile.class);
                startActivity(intent);
            }
        });
        binding.btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (context, settings.class);
                startActivity(intent);
            }
        });

        binding.btnMyLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, myLocations.class);
                startActivity(intent);

            }
        });

        binding.btnNotificatins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, notifications.class);
                startActivity(intent);
            }
        });

        binding.btnContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, contactUs.class);
                startActivity(intent);
            }
        });

    }

    private void readUserdata() {

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = mFire.collection("user").document(usuarioID);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot != null && documentSnapshot.exists()) {

                        if(documentSnapshot.getString("username").isEmpty()){
                            binding.textNameUser.setText(documentSnapshot.getString("fullname"));
                        }else{
                            binding.textNameUser.setText(documentSnapshot.getString("username"));
                        }
                    } 
                }
            }
        });

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference photoRef = storageRef.child("images/profile/" + usuarioID + "/profilephoto");
        Log.d("TESTE", " teste 1 ");

        photoRef.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("TESTE", " teste 2 " + uri);
                        Glide.with(getContext()).load(uri).into(binding.goToProfile);
                        Log.d("TESTE", " teste 3 " + uri);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}