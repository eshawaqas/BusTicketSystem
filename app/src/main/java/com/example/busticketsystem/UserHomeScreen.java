package com.example.busticketsystem;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DatabaseReference;
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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_screen);
        noReceiptFragment = new NoReceiptFragment();
        verificationInProgressFragment = new VerificationInProgressFragment();
        verifiedAccountFragment = new VerifiedAccountFragment();

        displayFragment();
    }


    private void displayFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Check conditions and show the appropriate fragment
        if (noReceiptImageInStorage()) {
            fragmentTransaction.replace(R.id.fragment_container, new NoReceiptFragment());
        } else if (verifiedAccount()) {
            fragmentTransaction.replace(R.id.fragment_container, new VerifiedAccountFragment());
        } else {
            fragmentTransaction.replace(R.id.fragment_container, new VerificationInProgressFragment());
        }

        fragmentTransaction.commit();
    }

    private boolean noReceiptImageInStorage() {
        // Implement your logic to check if there is no receipt image in the database storage
        // Return true if there is no receipt image, false otherwise
        return true; // Placeholder, replace with your implementation
    }

    private boolean verifiedAccount() {
        // Implement your logic to check if the verification is in progress
        // Return true if the verification is in progress, false otherwise
        return false; // Placeholder, replace with your implementation
    }
}