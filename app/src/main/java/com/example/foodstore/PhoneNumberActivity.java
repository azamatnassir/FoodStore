package com.example.foodstore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthOptions.Builder;
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken;
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class PhoneNumberActivity extends AppCompatActivity {
    @NotNull
    private String number = "";
    public FirebaseAuth auth;
    public String storedVerificationId;
    public ForceResendingToken resendToken;
    private OnVerificationStateChangedCallbacks callbacks;

    @NotNull
    public final String getNumber() {
        return this.number;
    }

    public final void setNumber(@NotNull String var1) {
        Intrinsics.checkNotNullParameter(var1, "<set-?>");
        this.number = var1;
    }

    @NotNull
    public final FirebaseAuth getAuth() {
        FirebaseAuth var10000 = this.auth;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("auth");
        }

        return var10000;
    }

    public final void setAuth(@NotNull FirebaseAuth var1) {
        Intrinsics.checkNotNullParameter(var1, "<set-?>");
        this.auth = var1;
    }

    @NotNull
    public final String getStoredVerificationId() {
        String var10000 = this.storedVerificationId;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("storedVerificationId");
        }

        return var10000;
    }

    public final void setStoredVerificationId(@NotNull String var1) {
        Intrinsics.checkNotNullParameter(var1, "<set-?>");
        this.storedVerificationId = var1;
    }

    @NotNull
    public final ForceResendingToken getResendToken() {
        ForceResendingToken var10000 = this.resendToken;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("resendToken");
        }

        return var10000;
    }

    public final void setResendToken(@NotNull ForceResendingToken var1) {
        Intrinsics.checkNotNullParameter(var1, "<set-?>");
        this.resendToken = var1;
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(1300006);
        FirebaseAuth var10001 = FirebaseAuth.getInstance();
        Intrinsics.checkNotNullExpressionValue(var10001, "FirebaseAuth.getInstance()");
        this.auth = var10001;
        ((Button)this.findViewById(1000010)).setOnClickListener((OnClickListener)(new OnClickListener() {
        public final void onClick(View it) {
            PhoneNumberActivity.this.login();
        }
    }));
        this.callbacks = (OnVerificationStateChangedCallbacks)(new OnVerificationStateChangedCallbacks() {
            public void onVerificationCompleted(@NotNull PhoneAuthCredential credential) {
                Intrinsics.checkNotNullParameter(credential, "credential");
                PhoneNumberActivity.this.startActivity(new Intent(PhoneNumberActivity.this.getApplicationContext(), MainActivity.class));
                PhoneNumberActivity.this.finish();
                Log.d("GFG", "onVerificationCompleted Success");
            }

            public void onVerificationFailed(@NotNull FirebaseException e) {
                Intrinsics.checkNotNullParameter(e, "e");
                Log.d("GFG", "onVerificationFailed  " + e);
            }

            public void onCodeSent(@NotNull String verificationId, @NotNull ForceResendingToken token) {
                Intrinsics.checkNotNullParameter(verificationId, "verificationId");
                Intrinsics.checkNotNullParameter(token, "token");
                Log.d("GFG", "onCodeSent: " + verificationId);
                PhoneNumberActivity.this.setStoredVerificationId(verificationId);
                PhoneNumberActivity.this.setResendToken(token);
                Intent intent = new Intent(PhoneNumberActivity.this.getApplicationContext(), VerifyActivity.class);
                intent.putExtra("storedVerificationId", PhoneNumberActivity.this.getStoredVerificationId());
                PhoneNumberActivity.this.startActivity(intent);
                PhoneNumberActivity.this.finish();
            }
        });
    }

    private final void login() {
        View var10001 = this.findViewById(1000031);
        Intrinsics.checkNotNullExpressionValue(var10001, "findViewById<EditText>(R.id.phone_number)");
        Editable var2 = ((EditText)var10001).getText();
        Intrinsics.checkNotNullExpressionValue(var2, "findViewById<EditText>(R.id.phone_number).text");
        this.number = StringsKt.trim((CharSequence)var2).toString();
        CharSequence var1 = (CharSequence)this.number;
        if (var1.length() > 0) {
            this.number = "+91" + this.number;
            this.sendVerificationCode(this.number);
        } else {
            Toast.makeText((Context)this, (CharSequence)"Enter mobile number", 0).show();
        }

    }

    private final void sendVerificationCode(String number) {
        FirebaseAuth var10000 = this.auth;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("auth");
        }

        Builder var3 = PhoneAuthOptions.newBuilder(var10000).setPhoneNumber(number).setTimeout(60L, TimeUnit.SECONDS).setActivity((Activity)this);
        OnVerificationStateChangedCallbacks var10001 = this.callbacks;
        if (var10001 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("callbacks");
        }

        PhoneAuthOptions var4 = var3.setCallbacks(var10001).build();
        Intrinsics.checkNotNullExpressionValue(var4, "PhoneAuthOptions.newBuil…acks\n            .build()");
        PhoneAuthOptions options = var4;
        PhoneAuthProvider.verifyPhoneNumber(options);
        Log.d("GFG", "Auth started");
    }
}
