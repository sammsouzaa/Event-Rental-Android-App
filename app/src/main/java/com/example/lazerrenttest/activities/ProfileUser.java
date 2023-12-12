package com.example.lazerrenttest.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lazerrenttest.databinding.ActyProfileuserBinding;
import com.example.lazerrenttest.fragmentsMenu.MainFragment;

public class ProfileUser extends AppCompatActivity {

    //não programei essa tela!!

    ActyProfileuserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActyProfileuserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProfileUser.this, MainFragment.class));

            }
        });
    }
}