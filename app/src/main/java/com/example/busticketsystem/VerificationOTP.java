package com.example.busticketsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerificationOTP extends AppCompatActivity {

    EditText inputno1, inputno2, inputno3, inputno4, inputno5, inputno6;
    Button verify;
    String getotpbackend;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_otp);

        inputno1 = findViewById(R.id.otpinput1);
        inputno2 = findViewById(R.id.otpinput2);
        inputno3 = findViewById(R.id.otpinput3);
        inputno4 = findViewById(R.id.otpinput4);
        inputno5 = findViewById(R.id.otpinput5);
        inputno6 = findViewById(R.id.otpinput6);
        verify = findViewById(R.id.submitbtnotp);

        TextView textView = findViewById(R.id.mobiletxt);
        textView.setText(String.format("+92-%s", getIntent().getStringExtra("Mobile")));

        getotpbackend = getIntent().getStringExtra("BackendOTP");
        ProgressBar progressBar = findViewById(R.id.verifypregressbar);


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!inputno1.getText().toString().trim().isEmpty() && !inputno2.getText().toString().trim().isEmpty() && !inputno3.getText().toString().trim().isEmpty() && !inputno4.getText().toString().trim().isEmpty() && !inputno5.getText().toString().trim().isEmpty() && !inputno6.getText().toString().trim().isEmpty()){
                    String entercodeotp = inputno1.getText().toString() +
                            inputno2.getText().toString() +
                            inputno3.getText().toString() +
                            inputno4.getText().toString() +
                            inputno5.getText().toString() +
                            inputno6.getText().toString();

                    if (getotpbackend != null)
                    {
                        progressBar.setVisibility(View.VISIBLE);
                        verify.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getotpbackend, entercodeotp);
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                verify.setVisibility(View.VISIBLE);

                                if (task.isSuccessful())
                                {
                                    Intent intent = new Intent(getApplicationContext(), UserHomeScreen.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                }else{
                                    Toast.makeText(VerificationOTP.this, "Enter correct OTP", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }else{
                        Toast.makeText(VerificationOTP.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                    }
//                    Toast.makeText(VerificationOTP.this, "OTP verify", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(VerificationOTP.this, "Enter all numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });

        numberOtpMove();

        TextView resendlabel = findViewById(R.id.resendotptxt);

        resendlabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+92" + getIntent().getStringExtra("Mobile"),
                        60,
                        TimeUnit.SECONDS,
                        VerificationOTP.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(VerificationOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                getotpbackend = s;
                                Toast.makeText(VerificationOTP.this, "OTP resend successful", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });
    }

    private void numberOtpMove() {
        inputno1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inputno2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputno2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inputno3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputno3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inputno4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputno4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inputno5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputno5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inputno6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}