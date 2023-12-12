package com.example.lazerrenttest.ADM.information;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.lazerrenttest.ADM.ADM_dashboard;
import com.example.lazerrenttest.R;
import com.example.lazerrenttest.databinding.ActivityInformationImovelBinding;
import com.example.lazerrenttest.databinding.ActivityInformationUserBinding;

public class information_user extends AppCompatActivity {

    ActivityInformationUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInformationUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
    @Override
    public void onBackPressed() {

        startActivity(new Intent(information_user.this, ADM_dashboard.class));

    }
}