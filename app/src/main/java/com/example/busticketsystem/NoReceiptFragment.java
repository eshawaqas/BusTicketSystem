package com.example.busticketsystem;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class NoReceiptFragment extends Fragment {
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;

    private ImageView profileImageView;
    private TextView nameTextView;

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


        // Fetch user data from the database
        fetchUserData();

        return view;
    }

    private void fetchUserData() {
//        if (currentUser != null) {
            //String userId = currentUser.getUid();
        String userId = "20F-0322";

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

