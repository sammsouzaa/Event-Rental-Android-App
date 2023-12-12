package com.example.lazerrenttest.ADM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.lazerrenttest.ADM.information.information_imovel;
import com.example.lazerrenttest.R;
import com.example.lazerrenttest.databinding.ActivityAdmPropertiesBinding;
import com.example.lazerrenttest.databinding.ActivityAdmSettingsBinding;

public class adm_settings extends AppCompatActivity {

    ActivityAdmSettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdmSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
    @Override
    public void onBackPressed() {

        startActivity(new Intent(adm_settings.this, ADM_dashboard.class));

    }
}