package com.example.busticketsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.os.AsyncTask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class SignUp extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    ImageView profilePictureImageView;
    private EditText Name, Email, RollNo, RouteNo, Password, ConfirmPassword;

    private Uri profilePictureUri;
    Button button;
    Button uploadbtn;
    private StorageReference storageReference;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://bus-pass-management-c51ef-default-rtdb.firebaseio.com/");
    String name, email, rollNo, routeNo, password, confirmPassword;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        storageReference = FirebaseStorage.getInstance().getReference("Uploads");

        Name = findViewById(R.id.nameEditText);
        Email = findViewById(R.id.emailEditText);
        RollNo = findViewById(R.id.rollnoEditText);
        RouteNo = findViewById(R.id.routenoEditText);
        Password = findViewById(R.id.passwordEditText);
        ConfirmPassword = findViewById(R.id.confirmpwEditText);
        profilePictureImageView = findViewById(R.id.profilePictureImageView);
        button = findViewById(R.id.signupButton);
        uploadbtn = findViewById(R.id.cameraButton);

        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = checks();
                if (isValid) {
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(rollNo))
                            {
                                Toast.makeText(SignUp.this, "User with this Roll No. already exists.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                databaseReference.child("Users").child(rollNo).child("Name").setValue(name);
                                databaseReference.child("Users").child(rollNo).child("Email").setValue(email);
                                databaseReference.child("Users").child(rollNo).child("Route").setValue(routeNo);
                                databaseReference.child("Users").child(rollNo).child("Password").setValue(password);
                                databaseReference.child("Users").child(rollNo).child("Name").setValue(name);
                                Toast.makeText(SignUp.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    // Validation failed, show error message
                    Toast.makeText(SignUp.this, "Validation unsuccessful", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void openFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            profilePictureUri = data.getData();
        }
        Picasso.with(this).load(profilePictureUri).into(profilePictureImageView);

    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadFile()
    {
        if (profilePictureImageView != null)
        {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(profilePictureUri));
            fileReference.putFile(profilePictureUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(SignUp.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
                    //Upload upload = new Upload(taskSnapshot.getDownloadUrl.toString());
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }
        else{
            Toast.makeText(this, "No file selected.", Toast.LENGTH_SHORT).show();
        }

    }
    
    private boolean checks()
    {
        // Retrieve user input values
        name = Name.getText().toString().trim();
        email = Email.getText().toString().trim();
        rollNo = RollNo.getText().toString().trim();
        routeNo = RouteNo.getText().toString().trim();
        password = Password.getText().toString().trim();
        confirmPassword = ConfirmPassword.getText().toString().trim();

        // Reset error messages
        Name.setError(null);
        Email.setError(null);
        RollNo.setError(null);
        RouteNo.setError(null);
        Password.setError(null);
        ConfirmPassword.setError(null);

        if (TextUtils.isEmpty(name)) {
            Name.setError("Name is required");
            Name.requestFocus();
            return false;
        }

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

        if (TextUtils.isEmpty(rollNo)) {
            RollNo.setError("Roll number is required");
            RollNo.requestFocus();
            return false;
        }

        if (!rollNo.matches("[0-9]{2}[A-Z]{1}-[0-9]{4}")) {
            RollNo.setError("Invalid roll number format. Use XXF-XXXX");
            RollNo.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(routeNo)) {
            RouteNo.setError("Route number is required");
            RouteNo.requestFocus();
            return false;
        }

        int routeNumber = Integer.parseInt(routeNo);
        if (routeNumber < 1 || routeNumber > 15) {
            RouteNo.setError("Invalid route number.");
            RouteNo.requestFocus();
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

        if (TextUtils.isEmpty(confirmPassword)) {
            ConfirmPassword.setError("Confirm password is required");
            ConfirmPassword.requestFocus();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            ConfirmPassword.setError("Passwords do not match");
            ConfirmPassword.requestFocus();
            return false;
        }

        if (profilePictureUri == null) {
            Toast.makeText(this, "Profile picture is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        // All checks passed
        return true;
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