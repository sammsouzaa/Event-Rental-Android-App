package com.example.lazerrenttest.intro;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lazerrenttest.ADM.ADM_dashboard;
import com.example.lazerrenttest.activities.MainActivity;
import com.example.lazerrenttest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acty_splash);

//        ProgressBar progressBar = findViewById(R.id.progressBar);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            progressBar.setIndeterminateTintList(ColorStateList.valueOf(Color.BLACK));
//        } else {
//            progressBar.getIndeterminateDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
//        }

        Log.d("Splash", "Abriu a acty");

        new Handler().postDelayed(() -> {
            Log.d("Splash", "entrou no handler");

            FirebaseUser currentUser = firebaseAuth.getInstance().getCurrentUser();

            if(currentUser != null){
                Log.d("Splash", "current user not null");
                String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                if(usuarioID.equals("X9GSvaeHH9NisgTadZlXSZV8DEI2")) {
                    startActivity(new Intent(SplashScreen.this, ADM_dashboard.class));
                    finish();
                }else{
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                }
            }
            else{
                Log.d("Splash", "not current user");
                startActivity(new Intent(SplashScreen.this, Intro.class));
                finish();
            }
        }, 1000);
    }
}