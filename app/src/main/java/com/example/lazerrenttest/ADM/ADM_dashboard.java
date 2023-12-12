package com.example.lazerrenttest.ADM;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lazerrenttest.R;
import com.example.lazerrenttest.authentication.Login;
import com.example.lazerrenttest.customDialog.Dialog_closeapp;
import com.example.lazerrenttest.databinding.ActivityAdmDashboardBinding;
import com.example.lazerrenttest.databinding.ActyMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ADM_dashboard extends AppCompatActivity {

    ActivityAdmDashboardBinding binding;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdmDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setOnClickListeners();

    }

    private void setOnClickListeners() {

        binding.btLogoutttt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ADM_dashboard.this, Login.class));
            }
        });

        binding.goToGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ADM_dashboard.this, adm_general.class));
            }
        });
        binding.goToUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ADM_dashboard.this, adm_users.class));

            }
        });
        binding.goToApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ADM_dashboard.this, adm_approval.class));

            }
        });
        binding.goToProperties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ADM_dashboard.this, adm_properties.class));

            }
        });
        binding.goToSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ADM_dashboard.this, adm_support.class));

            }
        });
        binding.goToSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ADM_dashboard.this, adm_settings.class));

            }
        });
    }

    boolean DoublePressToExit;


    @Override
    public void onBackPressed() {

        if(DoublePressToExit){

            Dialog_closeapp dialog_closeapp = new Dialog_closeapp(ADM_dashboard.this, ADM_dashboard.this);
            dialog_closeapp.setCancelable(false);
            dialog_closeapp.show();

        }
        else {
            DoublePressToExit = true;

            Toast toast = Toast.makeText(this, "Click Again", Toast.LENGTH_SHORT);
            toast.show();

            android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    DoublePressToExit = false;
                }
            }, 1500);

        }
    }
}