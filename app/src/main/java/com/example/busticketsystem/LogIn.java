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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class LogIn extends AppCompatActivity {

    EditText Email, Password;
    String email, password;
    FirebaseAuth auth;
    Button button;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        auth = FirebaseAuth.getInstance();

        Email = findViewById(R.id.editTextTextEmailAddress);
        Password = findViewById(R.id.editTextTextPassword);
        button = findViewById(R.id.LogInbutton);

        button.setOnClickListener((view -> {validateUser();}));
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if(auth.getCurrentUser() != null)
        {
            startActivity(new Intent(this, MainActivity2.class));
        }
        finish();
    }

    private boolean checks()
    {
        // Retrieve user input values
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();

        // Reset error messages
        Email.setError(null);
        Password.setError(null);


        if (TextUtils.isEmpty(email)) {
            Email.setError("Email is required");
            Email.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Invalid email address");
            Email.requestFocus();
            return false;
        }


        if (TextUtils.isEmpty(password)) {
            Password.setError("Password is required");
            Password.requestFocus();
            return false;
        }

        if (password.length() < 8 || !password.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=]).*$")) {
            Password.setError("Password must be at least 8 characters with one capital letter, one numeric digit, and one special character");
            Password.requestFocus();
            return false;
        }


        // All checks passed
        return true;
    }

    private void validateUser() {
        boolean isValid = checks();
        if (isValid) {
            new LoginUserAsyncTask().execute();
        } else {
            // Validation failed, show error message
            Toast.makeText(this, "Validation unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }

    private class LoginUserAsyncTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                // Simulate some delay for background task
                Thread.sleep(2000);

                // Perform login task in the background
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LogIn.this, "Log In Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LogIn.this, MainActivity2.class));
                        finish();
                    } else {
                        Toast.makeText(LogIn.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!result) {
                Toast.makeText(LogIn.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        }
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