package com.example.busticketsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class LogIn extends AppCompatActivity {

    EditText Email, Password;
    String email, password;
    Button button;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Email = findViewById(R.id.editTextTextEmailAddress);
        Password = findViewById(R.id.editTextTextPassword);
        button = findViewById(R.id.LogInbutton);

    }



    private void navigateToSplashScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void NavigateToSplashscreen(View view) {
        navigateToSplashScreen();
    }
}