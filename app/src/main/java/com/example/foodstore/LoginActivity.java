package com.example.foodstore;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private ImageView backBtn;
    private TextInputEditText textInputEditText;
    private TextInputEditText textInputEditText1;
    private Button loginBtn;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                startActivity(new Intent(LoginActivity.this, NavActivity.class));
                finish();
                Log.d(TAG, "onVerificationCompleted:");
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
                mResendToken = token;

                Intent intent = new Intent(LoginActivity.this, VerifyActivity.class);
                intent.putExtra("verificationId", verificationId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                progressBar.setVisibility(View.INVISIBLE);
                loginBtn.setEnabled(true);
                finish();
            }
        };

        backBtn = findViewById(R.id.login_back);
        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
        });

        progressBar = findViewById(R.id.login_progress);

        textInputEditText = findViewById(R.id.phone_number);
        textInputEditText1 = findViewById(R.id.phone_local);

        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(view -> {
            login();
        });
    }

    private void login() {
        String local = textInputEditText1.getText().toString().trim();
        String phone = textInputEditText.getText().toString().trim();
        // get the phone number from edit text and append the country cde with it
        if (phone.isEmpty()){
            Toast.makeText(this,"Enter phone number", Toast.LENGTH_SHORT).show();
        } else if (phone.length() != 10){
            Toast.makeText(this,"Invalid phone number", Toast.LENGTH_SHORT).show();
        } else {
            phone = local + phone;
            sendVerificationCode(phone);
            loginBtn.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .setForceResendingToken(mResendToken)     // ForceResendingToken from callbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        Log.d("GFG" , "Auth started");
    }
}