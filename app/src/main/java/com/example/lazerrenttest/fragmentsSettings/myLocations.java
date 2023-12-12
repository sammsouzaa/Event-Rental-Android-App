package com.example.lazerrenttest.fragmentsSettings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.example.lazerrenttest.activities.MainActivity;
import com.example.lazerrenttest.databinding.ActySettingsMylocationsBinding;
import com.example.lazerrenttest.sharedviewmodel.SharedViewModel;


public class myLocations extends AppCompatActivity {

    private SharedViewModel sharedViewModel;

    ActySettingsMylocationsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActySettingsMylocationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
    }
}