package com.example.lazerrenttest.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.example.lazerrenttest.authentication.Login;
import com.example.lazerrenttest.customDialog.Dialog_closeapp;
import com.example.lazerrenttest.databinding.ActyIntroBinding;

public class Intro extends AppCompatActivity {

    ActyIntroBinding binding;
    boolean DoublePressToExit;
    Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActyIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.goToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Intro.this, "aiaiai", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Intro.this, Login.class));
            }
        });
    }
    @Override
    public void onBackPressed() {

        if(DoublePressToExit){

            Dialog_closeapp dialog_closeapp = new Dialog_closeapp(Intro.this, Intro.this);
            dialog_closeapp.setCancelable(false);
            dialog_closeapp.show();

        }
        else {
            DoublePressToExit = true;

            toast = Toast.makeText(this, "Click Again", Toast.LENGTH_SHORT);
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