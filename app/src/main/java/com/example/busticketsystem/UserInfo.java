package com.example.busticketsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.squareup.picasso.Picasso;


public class UserInfo extends AppCompatActivity {

    TextView rollNo,name,email,password;
    EditText routeNo;
    ImageView userImage;
    ListView listView;
    String[] userInfo=new String[4];

    Button deleteBtn, verifyBtn,updateBtn;

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

        Toast.makeText(this,"Route Updated",Toast.LENGTH_SHORT).show();
    }

    private void verifyUser() {
        try {
            // Generate QR code based on the user's information
            String userData = userInfo[0] + "|" + userInfo[1] + "|" + userInfo[2] + "|" + userInfo[3];
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap qrCodeBitmap = barcodeEncoder.encodeBitmap(userData, BarcodeFormat.QR_CODE, 400, 400);

            // Pass the QR code bitmap to the VerifiedAccountFragment
            Bundle bundle = new Bundle();
            bundle.putParcelable("qrCode", qrCodeBitmap);
            VerifiedAccountFragment verifiedAccountFragment = new VerifiedAccountFragment();
            verifiedAccountFragment.setArguments(bundle);

            // Replace the current fragment with VerifiedAccountFragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, verifiedAccountFragment);
            fragmentTransaction.commit();

            Toast.makeText(this, "User Verified", Toast.LENGTH_SHORT).show();
        } catch (WriterException e) {
            e.printStackTrace();
        }
//        Toast.makeText(this,"User Verified",Toast.LENGTH_SHORT).show();
    }

    private void deleteUser() {

        Toast.makeText(this,"User Deleted",Toast.LENGTH_SHORT).show();
    }




}