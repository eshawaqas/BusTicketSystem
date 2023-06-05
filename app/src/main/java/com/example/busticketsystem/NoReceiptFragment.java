package com.example.busticketsystem;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class NoReceiptFragment extends Fragment {
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;

    private ImageView profileImageView;
    private TextView nameTextView;

    Button receiptImage;

    public NoReceiptFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the current user from Firebase Authentication
        // currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Get a reference to the Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance("https://bus-pass-management-c51ef-default-rtdb.firebaseio.com/").getReference("Users");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_no_receipt, container, false);

        // Initialize the UI elements
        profileImageView = view.findViewById(R.id.homescreenimg);
        nameTextView = view.findViewById(R.id.homescreennametxt);
        receiptImage = view.findViewById(R.id.uploadbtn);

        receiptImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
            }
        });


        // Fetch user data from the database
        fetchUserData();

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            // Get the selected image URI from the intent
            Uri imageUri = data.getData();

            // Upload the image to Firebase Storage and get the download URL
            uploadImageToFirebase(imageUri);
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        // Generate a unique receipt ID
        String receiptId = UUID.randomUUID().toString();

        // Create a reference to the Firebase Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("receipts").child(receiptId);

        // Upload the image file to Firebase Storage
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get the download URL of the uploaded image
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUrl) {
                                // Store the download URL in the user's Firebase Realtime Database under the receipt ID
                                String userId = "19F-0987";
                                databaseReference.child(userId).child("receipts").setValue(downloadUrl.toString());

                                Toast.makeText(getContext(), "Receipt saved!", Toast.LENGTH_SHORT).show();
                                //Log.d("TAG", "Image URL saved in Firebase: " + downloadUrl.toString());
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "Image upload failed: " + e.getMessage());
                    }
                });
    }


    private void fetchUserData() {
//        if (currentUser != null) {
        //String userId = currentUser.getUid();
        String userId = "19F-0987";

        // Retrieve user data from the database based on the user's ID
        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);

                    // Update the UI with the retrieved user data
                    if (user != null) {
                        nameTextView.setText(user.getName());
                        Log.d("TAG", "Name: " + user.getName());

                        // Load the profile picture using Picasso or any other image loading library
                        Picasso.with(getContext()).load(user.getProfilePictureUrl()).into(profileImageView);
                        Log.d("TAG", "Profile Picture URL: " + user.getProfilePictureUrl());
                    }
                } else {
                    Log.d("TAG", "Snapshot does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "Database Error: " + error.getMessage()); // Add this line for debugging
            }
        });
//        }
    }

}

