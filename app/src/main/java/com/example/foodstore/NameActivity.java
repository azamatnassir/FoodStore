package com.example.foodstore;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NameActivity extends AppCompatActivity {

    private ImageView backBtn;
    private Button order;
    private TextInputEditText textInputEditText;
    private TextInputEditText address;
    private TextInputEditText apartment;
    private TextInputEditText entrance;
    private TextInputEditText floor;
    private ProgressBar progressBar;

    private CardView map;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db;
    private DocumentReference docRef;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        db = FirebaseFirestore.getInstance();

        backBtn = findViewById(R.id.verify_back);
        backBtn.setOnClickListener(view -> {
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });  //back button listener

        map = findViewById(R.id.address_map);
        map.setOnClickListener(view -> {

        });

        textInputEditText = findViewById(R.id.user_name);
        address = findViewById(R.id.user_address);
        apartment = findViewById(R.id.address_kv);
        entrance = findViewById(R.id.address_door);
        floor = findViewById(R.id.address_floor);

        progressBar = findViewById(R.id.verify_progress);

        order = findViewById(R.id.verify_btn);
        order.setOnClickListener(view -> {
            String name = textInputEditText.getText().toString().trim();
            String addressText = address.getText().toString().trim();
            String apartmentText = apartment.getText().toString().trim();
            String entranceText = entrance.getText().toString().trim();
            String floorText = floor.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
            } else {
                progressBar.setVisibility(View.VISIBLE);
                order.setEnabled(false);
                saveUser(name);
                saveAddress(addressText, apartmentText, entranceText, floorText);
            }
        });  //verify  button listener
    }

    private void saveAddress(String addressText, String apartmentText, String entranceText, String floorText) {
        Map<String, String> userAddress = new HashMap<>();
        userAddress.put("address", addressText);
        userAddress.put("apartment", apartmentText);
        userAddress.put("entrance", entranceText);
        userAddress.put("floor", floorText);

        db.collection("users").document(firebaseUser.getPhoneNumber()).collection("address")
                .document("delivery")
                .set(userAddress)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(NameActivity.this, NavActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NameActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    private void saveUser(String name) {
        user = new User(name, 0, 0);

        db.collection("users").document(firebaseUser.getPhoneNumber())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.w(TAG, "Success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NameActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }
}