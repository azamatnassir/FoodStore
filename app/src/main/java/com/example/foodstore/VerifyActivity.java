package com.example.foodstore;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class VerifyActivity extends AppCompatActivity {

    private String verificationId;

    private ImageView backBtn;
    private Button verify;
    private TextInputEditText textInputEditText;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db;
    private DocumentReference docRef;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();

        verificationId = getIntent().getStringExtra("verificationId");

        backBtn = findViewById(R.id.verify_back);
        backBtn.setOnClickListener(view -> {
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });  //back button listener

        textInputEditText = findViewById(R.id.verify_code);

        progressBar = findViewById(R.id.verify_progress);

        verify = findViewById(R.id.verify_btn);
        verify.setOnClickListener(view -> {
            String otp = textInputEditText.getText().toString().trim();

            if (otp.isEmpty()) {
                Toast.makeText(this, "Please enter code", Toast.LENGTH_SHORT).show();
            } else {
                progressBar.setVisibility(View.VISIBLE);
                verify.setEnabled(false);
                verifyCode(otp);
            }
        });  //verify  button listener
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        checkUserReg();
                    } else {
                        Toast.makeText(VerifyActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }  //if code is correct redirect to menu page

    private void checkUserReg(){
        firebaseUser = mAuth.getCurrentUser();
        docRef = db.collection("users").document(firebaseUser.getPhoneNumber());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Intent i;
                    if (document.exists()) {
                        i = new Intent(VerifyActivity.this, NavActivity.class);
                    } else {
                        i = new Intent(VerifyActivity.this, NameActivity.class);
                    }
                    startActivity(i);
                    progressBar.setVisibility(View.INVISIBLE);
                    verify.setEnabled(true);
                    finish();
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }  //check the code with database
}