package com.example.busticketsystem;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class AdminDashboardAdapter extends RecyclerView.Adapter<AdminDashboardAdapter.ViewHolder> {

    ArrayList<HashMap<String, String>> list;
    FirebaseDatabase database;
    DatabaseReference reference;
    Context context;
    Intent intent;

    public AdminDashboardAdapter(ArrayList<HashMap<String, String>> list,Context context) {
        Log.d("TAG","Constructor");
        this.list = list;
        this.context=context;
        Log.d("TAG",this.list.toString());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_admin_dashboard,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HashMap<String ,String> tempUser=list.get(position);
        User user=new User(
                tempUser.get("name"),
                tempUser.get("email"),
                tempUser.get("rollNo"),
                tempUser.get("routeNo"),
                tempUser.get("password"),
                tempUser.get("profilePictureUrl")
        );

        holder.userName.setText(user.getName());
        Picasso.with(holder.itemView.getContext()).load(user.getProfilePictureUrl()).into(holder.userImage);


        holder.infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(v.getContext(),UserInfo.class);
                intent.putExtra("rollNo",user.getRollNo());
                intent.putExtra("name",user.getName());
                intent.putExtra("email",user.getEmail());
                intent.putExtra("password",user.getPassword());
                intent.putExtra("url",user.getProfilePictureUrl());
                intent.putExtra("route",user.getRouteNo());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView userImage;
        Button infoBtn,deleteBtn;
        TextView userName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userImage=itemView.findViewById(R.id.userImage);
            userName=itemView.findViewById(R.id.userName);
            infoBtn=itemView.findViewById(R.id.infoBtn);
        }
    }


}
