package com.example.lazerrenttest.ADM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.lazerrenttest.ADM.information.information_imovel;
import com.example.lazerrenttest.R;
import com.example.lazerrenttest.databinding.ActivityAdmSettingsBinding;
import com.example.lazerrenttest.databinding.ActivityAdmSupportBinding;

public class adm_support extends AppCompatActivity {

    ActivityAdmSupportBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdmSupportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
    @Override
    public void onBackPressed() {

        startActivity(new Intent(adm_support.this, ADM_dashboard.class));

    }
}