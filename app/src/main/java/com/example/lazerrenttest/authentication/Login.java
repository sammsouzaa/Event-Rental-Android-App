package com.example.lazerrenttest.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.lazerrenttest.ADM.ADM_dashboard;
import com.example.lazerrenttest.R;
import com.example.lazerrenttest.activities.MainActivity;
import com.example.lazerrenttest.databinding.ActyLoginBinding;
import com.example.lazerrenttest.intro.Intro;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    private ActyLoginBinding binding;

    private View v;

    private String email;
    private String password;

    private String [] message = {"fill in all the data", "Login Success"};

    private Boolean senha1Visivel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActyLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth=firebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        binding.IDPassowrd.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int Right = 2;
                if (event.getAction()== MotionEvent.ACTION_UP){
                    if (event.getRawX()>=binding.IDPassowrd.getRight()-binding.IDPassowrd.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = binding.IDPassowrd.getSelectionEnd();
                        if (senha1Visivel){
                            binding.IDPassowrd.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.baseline_visibility_off_24,0);
                            binding.IDPassowrd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            senha1Visivel=false;
                        } else {
                            binding.IDPassowrd.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.baseline_visibility_24,0);
                            binding.IDPassowrd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            senha1Visivel=true;
                        }
                        binding.IDPassowrd.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Login.this, Signup.class));
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = binding.IDEmail.getText().toString().trim();
                password = binding.IDPassowrd.getText().toString().trim();

                if(email.isEmpty() || password.isEmpty()){
                    Snackbar snackbar = Snackbar.make(v, message[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{

                    loginAuthentication();
                }
            }
        });

        binding.resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setTitle("Sending Mail");
                progressDialog.show();

                email = binding.IDEmail.getText().toString().trim();

                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressDialog.cancel();
                                Toast.makeText(Login.this, "Email Sent", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.cancel();
                                Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
}
    private void loginAuthentication() {

        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        Log.d("Login", "logou");

                        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        if(usuarioID.equals("X9GSvaeHH9NisgTadZlXSZV8DEI2")){
                            progressDialog.cancel();
                            startActivity(new Intent(Login.this, ADM_dashboard.class));
                            Toast.makeText(Login.this, message[1], Toast.LENGTH_SHORT).show();
                        }else{
                            progressDialog.cancel();
                            startActivity(new Intent(Login.this, MainActivity.class));
                            Toast.makeText(Login.this, message[1], Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.cancel();
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(Login.this, Intro.class));

    }
}