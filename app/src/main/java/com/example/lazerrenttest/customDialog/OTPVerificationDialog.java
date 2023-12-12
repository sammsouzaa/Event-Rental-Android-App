package com.example.lazerrenttest.customDialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.lazerrenttest.R;
import com.example.lazerrenttest.registers.CreateUser;
import com.example.lazerrenttest.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Random;

public class OTPVerificationDialog  extends Dialog {

    private String name;
    private String email;
    private String cpf;
    private String phone;
    private String password;
    private String phoneTxt;

    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    Context context;

    private String numberCode;
    private EditText otpET1,otpET2,otpET3,otpET4;
    private TextView resendBtn;
    private TextView wrongNumberBtn;
    private Button verifyBtn;
    private int selectedETPosition = 0;

    private Random random = new Random();

    private ArrayList numberCodeArr = new ArrayList(4);

    private UserModel dataUser;

    //resend otp time in seconds
    private int resendTime = 60;

    //will be true after 60 seconds
    private boolean resendEnable = false;

    public OTPVerificationDialog(@NonNull Context context, UserModel dataUser, String password, String phoneTxt) {
        super(context);

        this.name = dataUser.getFullname();
        this.email = dataUser.getEmail();
        this.cpf = dataUser.getCpf();
        this.phone = dataUser.getPhone();

        this.password = password;
        this.phoneTxt = phoneTxt;

        this.context = context;
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(android.R.color.transparent)));
        setContentView(R.layout.dialog_otp_layout);

        int numerous = random.nextInt(8999) + 1000;

        numberCode = Integer.toString(numerous);

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone, null, numberCode, null, null);
        Toast.makeText(getContext(), "Code Sent", Toast.LENGTH_LONG).show();

        Toast.makeText(getContext(), numberCode, Toast.LENGTH_LONG).show();

        otpET1 = findViewById(R.id.otpET1);
        otpET2 = findViewById(R.id.otpET2);
        otpET3 = findViewById(R.id.otpET3);
        otpET4 = findViewById(R.id.otpET4);

        resendBtn = findViewById(R.id.resendBtn);
        verifyBtn = findViewById(R.id.verifyBtn);
        wrongNumberBtn = findViewById(R.id.wrongNumberBtn);

        final TextView mobileNumber = findViewById(R.id.mobileNumber);

        otpET1.addTextChangedListener(textWatcher);
        otpET2.addTextChangedListener(textWatcher);
        otpET3.addTextChangedListener(textWatcher);
        otpET4.addTextChangedListener(textWatcher);

        //By deffault open the keyboard on first EditText

        showKeyboard(otpET1);

        //Start the CountDown timer;

        startCountDownTimer();

        mobileNumber.setText(phoneTxt);


        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (resendEnable == true){

                    int numerous = random.nextInt(8999) + 1000;

                    numberCode = Integer.toString(numerous);

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phone, null, numberCode, null, null);
                    Toast.makeText(getContext(), "Code Sent", Toast.LENGTH_LONG).show();

                    startCountDownTimer();
                }
            }
        });

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getOTP = otpET1.getText().toString() + otpET2.getText().toString() + otpET3.getText().toString() + otpET4.getText().toString();
                getOTP = getOTP.trim();

                if (getOTP.length() == 4){

                    if(getOTP.equals(numberCode)){

                        callCreateUser();

                    }else{

                        Toast.makeText(getContext(), "Incorrect Code", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

        wrongNumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    private void callCreateUser() {
        CreateUser createUser = new CreateUser(context, dataUser, password);
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if (s.length() > 0) {
                if (selectedETPosition == 0) {

                    //select next EditText
                    selectedETPosition = 1;
                    showKeyboard(otpET2);

                }
                else if (selectedETPosition == 1) {

                    //select next EditText
                    selectedETPosition = 2;
                    showKeyboard(otpET3);

                }
                else if (selectedETPosition == 2) {

                    //select next EditText
                    selectedETPosition = 3;
                    showKeyboard(otpET4);

                }else {
                    verifyBtn.setBackgroundColor(R.drawable.round_back_red_100);
                }
            }
        }
    };

    private void showKeyboard(EditText otpET1){

        otpET1.requestFocus();

        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpET1, InputMethodManager.SHOW_IMPLICIT);

    }

    private void startCountDownTimer(){

        resendEnable = false;
        resendBtn.setTextColor(Color.parseColor("#990000"));

        new CountDownTimer(resendTime * 1000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                resendBtn.setText("Resend Code ("+(millisUntilFinished / 1000 )+")");
            }

            @Override
            public void onFinish() {
                resendEnable = true;
                resendBtn.setText("Resend Code");
                resendBtn.setTextColor(Color.parseColor("#301E67"));
//                resendBtn.setTextColor(getContext().getResources().getColor(android.R.color.black));
            }
        }.start();

    }

    @Override
    public boolean onKeyUp(int keyCode, @NonNull KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_DEL){

            if(selectedETPosition == 3){

                //select previous EditText
                selectedETPosition = 2;
                showKeyboard(otpET3);

            } else if (selectedETPosition == 2) {

                //select previous EditText
                selectedETPosition = 1;
                showKeyboard(otpET2);

            }else if (selectedETPosition == 1) {

                //select previous EditText
                selectedETPosition = 0;
                showKeyboard(otpET1);

            }
            verifyBtn.setBackgroundResource(R.drawable.round_back_brown100);
            return true;

        }
        else {
            return super.onKeyUp(keyCode, event);
        }
    }
}
