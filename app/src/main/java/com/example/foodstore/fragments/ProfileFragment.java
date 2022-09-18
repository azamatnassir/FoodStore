package com.example.foodstore.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodstore.LoginActivity;
import com.example.foodstore.R;
import com.example.foodstore.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {

    private TextView phone;
    private TextView name;
    private TextView amount;
    private TextView total;
    private Button logOut;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db;
    private DocumentReference docRef;
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        db = FirebaseFirestore.getInstance();

        phone = view.findViewById(R.id.user_phone);
        name = view.findViewById(R.id.user_name);
        amount = view.findViewById(R.id.profile_amount);
        total = view.findViewById(R.id.profile_total);
        logOut = view.findViewById(R.id.profile_edit);

        phone.setText(firebaseUser.getPhoneNumber());

        logOut.setOnClickListener(view1 -> {
            new AlertDialog.Builder(view.getContext())
                    .setMessage("Change personal data or sign out")
                    .setCancelable(false)
                    .setNegativeButton("Change", null)
                    .setPositiveButton("Sign Out", (dialogInterface, i) -> {
                        mAuth.signOut();
                        Intent intent = new Intent(view.getContext(), LoginActivity.class);
                        startActivity(intent);
                    })
                    .show();
        });

        setProfileData();
    }

    public void setProfileData() {
        firebaseUser = mAuth.getCurrentUser();
        docRef = db.collection("users").document(firebaseUser.getPhoneNumber());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
                if (user.getName() == null){
                    name.setText("-");
                } else {
                    name.setText(user.getName());
                }
                if (user.getName() == null){
                    amount.setText("-");
                } else {
                    amount.setText(String.valueOf(user.getAmount()));
                }
                if (user.getName() == null){
                    total.setText("-");
                } else {
                    total.setText(String.valueOf(user.getTotal()));
                }
            }
        });
    }
}
