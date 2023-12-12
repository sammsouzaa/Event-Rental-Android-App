package com.example.lazerrenttest.fragmentsSettings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lazerrenttest.activities.MainActivity;
import com.example.lazerrenttest.databinding.ActySettingsContactusBinding;

public class contactUs extends AppCompatActivity {

    ActySettingsContactusBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActySettingsContactusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.closebutao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}