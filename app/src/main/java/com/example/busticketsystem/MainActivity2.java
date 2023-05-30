package com.example.busticketsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity2 extends AppCompatActivity {

    TextView name, email;
    Button button;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        name = findViewById(R.id.nametxt2);
        email = findViewById(R.id.gmailtxt);
        button = findViewById(R.id.logout);


        if (user != null)
        {
            name.setText(user.getDisplayName());
            email.setText(user.getEmail());

        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(MainActivity2.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (user == null)
        {
            startActivity(new Intent(this, LogIn.class));
            finish();
        }
    }
}


