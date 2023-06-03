package com.example.busticketsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminDashboard extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<HashMap<String,String>> userList=new ArrayList<>();
    AdminDashboardAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        getUserData();
    }

    private void getUserData() {
        database=FirebaseDatabase.getInstance("https://bus-pass-management-c51ef-default-rtdb.firebaseio.com/");
        reference=database.getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue()==null)
                {
                    Log.d("TAG","No Data Retrieved from Firebase");
                }
                else
                {
                    for(DataSnapshot ss:snapshot.getChildren()){
                        HashMap<String,String> temp= (HashMap<String, String>) ss.getValue();
                        userList.add(temp);
//
//                        Log.d("TAG",temp.toString());
//                        Log.d("TAG",ss.getValue().toString());
                    }

                    recyclerView=findViewById(R.id.rvAdmin);
                    adapter=new AdminDashboardAdapter(userList,null);
                    RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}