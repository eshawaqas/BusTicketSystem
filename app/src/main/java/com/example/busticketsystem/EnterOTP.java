package com.example.busticketsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class EnterOTP extends AppCompatActivity {

    EditText enternumber;
    Button getoptbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        enternumber = findViewById(R.id.entermobileedttxt);
        getoptbtn = findViewById(R.id.sendotpbtn);

        ProgressBar progressBar = findViewById(R.id.sendotpprogressbar);

        getoptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!enternumber.getText().toString().trim().isEmpty())
                {
                    if((enternumber.getText().toString().trim().length()) == 10){
                        progressBar.setVisibility(View.VISIBLE);
                        getoptbtn.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber("+92" + enternumber.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                EnterOTP.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        getoptbtn.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        getoptbtn.setVisibility(View.VISIBLE);
                                        Toast.makeText(EnterOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        getoptbtn.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(),VerificationOTP.class);
                                        intent.putExtra("Mobile", enternumber.getText().toString());
                                        intent.putExtra("BackendOTP", s);
                                        startActivity(intent);
                                    }
                                }
                        );


                    }
                    else
                    {
                        Toast.makeText(EnterOTP.this, "Enter correct mobile number", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(EnterOTP.this, "Enter mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}