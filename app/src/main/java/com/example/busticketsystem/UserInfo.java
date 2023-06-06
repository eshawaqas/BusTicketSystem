package com.example.busticketsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class UserInfo extends AppCompatActivity {

    TextView rollNo,name,email,password;
    EditText routeNo;
    ImageView userImage;
    ListView listView;
    String[] userInfo=new String[4];

    Button deleteBtn, verifyBtn,updateBtn;
    ImageView iv_qr;
    FirebaseDatabase database;
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        listView=findViewById(R.id.listView);
        deleteBtn=findViewById(R.id.deleteBtn);
        verifyBtn=findViewById(R.id.verifyBtn);
        updateBtn=findViewById(R.id.updateBtn);
        userImage=findViewById(R.id.profilePictureImageView);
        routeNo=findViewById(R.id.routenoEditText);
        //iv_qr=findViewById(R.id.qrimg);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRoute();
            }
        });

        Intent intent=getIntent();
        userInfo[0]=intent.getStringExtra("rollNo");
        userInfo[1]=intent.getStringExtra("name");
        userInfo[2]=intent.getStringExtra("email");
        userInfo[3]=intent.getStringExtra("password");

        routeNo.setText(intent.getStringExtra("route"));
        Picasso.with(this).load(intent.getStringExtra("url")).into(userImage);

        UserInfoListViewAdapter adapter=new UserInfoListViewAdapter(this,userInfo);
        listView.setAdapter(adapter);
    }


    private void updateRoute() {
        String newRoute=routeNo.getText().toString();
        database=FirebaseDatabase.getInstance("https://bus-pass-management-c51ef-default-rtdb.firebaseio.com/");
        reference=database.getReference("Users");

        reference.child(userInfo[0]).child("rollNo").setValue(newRoute);

        Toast.makeText(this,"Route Updated",Toast.LENGTH_SHORT).show();
    }


    private void verifyUser() {


        // Get the user's roll number
        //String rollNo = userInfo[0];
        String rollNo = "20F-0322";

        // Get a reference to the Firebase database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // isme condition dalni hai ke agar image pari huwi hai receipt ko tb ye kaam ho else wo boldey image doesnt exist
        // Update the status of the user to "verified"
        databaseReference.child(rollNo).child("verified").setValue(true)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UserInfo.this, "User Verified", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(UserInfo.this, "Failed to verify user", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void deleteUser() {
        database=FirebaseDatabase.getInstance("https://bus-pass-management-c51ef-default-rtdb.firebaseio.com/");
        reference=database.getReference("Users");
        reference.child(userInfo[0]).removeValue();

        Toast.makeText(this,"User Deleted",Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(UserInfo.this,AdminDashboard.class);
        startActivity(intent);
    }


    public void updateRoute(View view) {

    }
}