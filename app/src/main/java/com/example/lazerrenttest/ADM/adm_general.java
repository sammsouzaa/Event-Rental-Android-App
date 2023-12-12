package com.example.lazerrenttest.ADM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.lazerrenttest.R;
import com.example.lazerrenttest.databinding.ActivityAdmApprovalBinding;
import com.example.lazerrenttest.databinding.ActivityAdmGeneralBinding;

public class adm_general extends AppCompatActivity {

    ActivityAdmGeneralBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdmGeneralBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
    @Override
    public void onBackPressed() {

        startActivity(new Intent(adm_general.this, ADM_dashboard.class));

    }
}