package com.example.foodstore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button loginBtn;
    private Button supportBtn;
    private Spinner lang_spinner;

    ArrayList<Languages> languages;
    LanguageAdapter adapter;
    Context context = this;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        loginBtn = findViewById(R.id.main_btn);
        loginBtn.setOnClickListener(view -> {
            Intent intent;
            if (user != null) {
                intent = new Intent(this, NavActivity.class);
            } else {
                intent = new Intent(this, LoginActivity.class);
            }
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
        });

        supportBtn = findViewById(R.id.main_support);
        supportBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, SupportMainActivity.class);
            startActivity(intent);
        });
//        lang_spinner = findViewById(R.id.lang_spinner);

//        initList();
//        adapter = new LanguageAdapter(this, languages);
//        lang_spinner.setAdapter(adapter);
//
//        lang_spinner.setOnItemSelectedListener(
//                new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent,
//                                               View view, int position, long id) {
//                        Languages clickedItem = (Languages)
//                                parent.getItemAtPosition(position);
//                        String name = clickedItem.getLanguage();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//                    }
//                });
    }

//    private void initList() {
//        languages = new ArrayList<>();
//        languages.add(new Languages("English", context.getResources().getDrawable(R.drawable.ic_eng)));
//        languages.add(new Languages("Kazakh", context.getResources().getDrawable(R.drawable.ic_kaz)));
//        languages.add(new Languages("Russian", context.getResources().getDrawable(R.drawable.ic_rus)));
//    }

//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        String text = adapterView.getItemAtPosition(i).toString();
//        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isNetworkConnected()){
            new AlertDialog.Builder(this)
                    .setMessage("No internet connection")
                    .setCancelable(false)
                    .setNegativeButton("Exit", ((dialogInterface, i) -> {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }))
                    .setPositiveButton("Restart", (dialogInterface, i) -> {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    })
                    .show();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialogInterface, i) -> finish())
                .setNegativeButton("No", null)
                .show();

    }
}