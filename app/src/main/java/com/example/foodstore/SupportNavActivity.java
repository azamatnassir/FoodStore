package com.example.foodstore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class SupportNavActivity extends AppCompatActivity {

    private TextInputEditText editText1;
    private TextInputEditText editText2;
    private TextInputEditText editText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_nav);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Support");
        actionBar.setDisplayHomeAsUpEnabled(true);

        editText1 = findViewById(R.id.support_nav_name);
        editText2 = findViewById(R.id.support_nav_title);
        editText3 = findViewById(R.id.support_nav_description);

        Button button = findViewById(R.id.support_nav_btn);
        button.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage(){

        String name = editText1.getText().toString();
        String title = editText2.getText().toString();
        String message = editText3.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"azamatnsr@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, name + "\n" + message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an app to send email"));
    }
}