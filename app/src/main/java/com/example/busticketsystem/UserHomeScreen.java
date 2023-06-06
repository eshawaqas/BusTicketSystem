package com.example.busticketsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class UserHomeScreen extends AppCompatActivity {

    private NoReceiptFragment noReceiptFragment;
    private VerificationInProgressFragment verificationInProgressFragment;
    private VerifiedAccountFragment verifiedAccountFragment;

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView uploadImageView;
    private Bitmap selectedImageBitmap;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    String email,password,roll;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_screen);
//        noReceiptFragment = new NoReceiptFragment();
        verificationInProgressFragment = new VerificationInProgressFragment();
        verifiedAccountFragment = new VerifiedAccountFragment();

        Intent intent=getIntent();
        email=intent.getStringExtra("Email");
        password=intent.getStringExtra("Password");
        roll=intent.getStringExtra("Rollno");

        displayFragment();
    }


    private void displayFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        String userId = roll; // Replace with the actual user ID



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();



        databaseReference.child("Users").child(userId).child("receipts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    fragmentTransaction.replace(R.id.fragment_container, new VerifiedAccountFragment());
                } else {
                    fragmentTransaction.replace(R.id.fragment_container, new NoReceiptFragment(roll));
                }
                fragmentTransaction.commit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error condition
            }
        });
    }

}