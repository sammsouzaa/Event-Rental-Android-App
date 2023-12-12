package com.example.lazerrenttest.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.lazerrenttest.R;
import com.example.lazerrenttest.registers.CreateUser;
import com.example.lazerrenttest.databinding.ActySignupBinding;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.lazerrenttest.model.UserModel;

public class Signup extends AppCompatActivity {

//    private EditText cdName;
//    private EditText cdEmail;
//    private EditText cdPassword;
//    private ImageView btnRegister;

    private String fullname;
    private String email;
    private String password;
    private String cpf;
    private String phone;




    private View v;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;


    String message = "fill in all the data";

    ActySignupBinding binding;

    boolean nothing;

    boolean senha1Visivel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        ActivityCompat.requestPermissions(this, new String[]{
//                Manifest.permission.SEND_SMS
//        }, 1);

        binding.IDcdPassowrd.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int Right = 2;
                if (event.getAction()== MotionEvent.ACTION_UP){
                    if (event.getRawX()>=binding.IDcdPassowrd.getRight()-binding.IDcdPassowrd.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = binding.IDcdPassowrd.getSelectionEnd();
                        if (senha1Visivel){
                            binding.IDcdPassowrd.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.baseline_visibility_off_24,0);
                            binding.IDcdPassowrd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            senha1Visivel=false;
                        } else {
                            binding.IDcdPassowrd.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.baseline_visibility_24,0);
                            binding.IDcdPassowrd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            senha1Visivel=true;
                        }
                        binding.IDcdPassowrd.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        CreateMask();

        mAuth = mAuth.getInstance();
        firebaseFirestore = firebaseFirestore.getInstance();

//        StartComponents();

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fullname = binding.IDcdName.getText().toString();
                email = binding.IDcdEmail.getText().toString().trim();
                password = binding.IDcdPassowrd.getText().toString();
                cpf = binding.IDcdCpf.getText().toString();
                phone = binding.IDcdNumber.getText().toString();

                dataRecovery();

            }
        });
    }

    private void CreateMask() {

        SimpleMaskFormatter smf = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(binding.IDcdNumber, smf);
        binding.IDcdNumber.addTextChangedListener(mtw);

        SimpleMaskFormatter smf2 = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw2 = new MaskTextWatcher(binding.IDcdCpf, smf2);
        binding.IDcdCpf.addTextChangedListener(mtw2);
    }

    private void dataRecovery() {
        if (binding.IDcdName.getText().toString().trim().isEmpty() || binding.IDcdEmail.getText().toString().trim().isEmpty()
                || binding.IDcdPassowrd.getText().toString().trim().isEmpty() || binding.IDcdCpf.getText().toString().trim().isEmpty() ||
                binding.IDcdNumber.getText().toString().trim().isEmpty()){

            Toast.makeText(this, "These fields can't be empty", Toast.LENGTH_SHORT).show();

        }else if(binding.IDcdCpf.getText().toString().trim().length() < 11){
            Toast.makeText(this, "Enter your cpf correctly", Toast.LENGTH_SHORT).show();

        }else if(binding.IDcdNumber.getText().toString().trim().length() < 11){
            Toast.makeText(this, "Enter your number correctly", Toast.LENGTH_SHORT).show();

        }else if( binding.IDcdPassowrd.getText().toString().trim().length() < 6 ){
            Toast.makeText(this, "Password must be least 6 characters", Toast.LENGTH_SHORT).show();
        }
        else{
            String phoneTxt = phone;

            phone = phone.replace("(", "");
            phone = phone.replace(")", "");
            phone = phone.replace("-", "");

            UserModel dataUser = new UserModel();

            dataUser.setFullname(fullname);
            dataUser.setUsername("");
            dataUser.setEmail(email);
            dataUser.setCpf(cpf);
            dataUser.setPhone(phoneTxt);
            dataUser.setImoveis("0");
            dataUser.setDate("00/00/00");

            CreateUser createUser = new CreateUser(Signup.this, dataUser, password);

//            if(ActivityCompat.checkSelfPermission(signup.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
//
//                ActivityCompat.requestPermissions(signup.this, new String[]{Manifest.permission.SEND_SMS}, 1);
//
//            }
//            else if(ActivityCompat.checkSelfPermission(signup.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED){
//                Toast.makeText(this, "Permision Denied", Toast.LENGTH_SHORT).show();
//            }
//            else {
//                OTPVerificationDialog otpVerificationDialog = new OTPVerificationDialog(signup.this, dataUser, password, phoneTxt);
//                otpVerificationDialog.setCancelable(false);
//                otpVerificationDialog.show();
//            }
        }
    }


//        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//
//                    startActivity(new Intent(signup.this, login.class));
//
//                    Toast.makeText(signup.this, "Register Successful", Toast.LENGTH_SHORT).show();
//
//                }else{
//                    Snackbar snackbar = Snackbar.make(v, messages[2],Snackbar.LENGTH_SHORT);
//                    snackbar.setBackgroundTint(Color.WHITE);
//                    snackbar.setTextColor(Color.BLACK);
//                    snackbar.show();
//                }
//            }
//        });

}