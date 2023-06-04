package com.example.busticketsystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UserInfoListViewAdapter extends ArrayAdapter<String> {

    String[] userInfo;
    Activity activity;
    String[] tags={"RollNo","Name","Email","Password"};

    public UserInfoListViewAdapter(@NonNull Activity context, String[] userInfo) {
        super(context, R.layout.user_info_lv,userInfo);

        this.userInfo=userInfo;
        this.activity=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=activity.getLayoutInflater();
        View view=inflater.inflate(R.layout.user_info_lv,null);

        TextView tv1=view.findViewById(R.id.tag);
        TextView tv2=view.findViewById(R.id.content);

        tv1.setText(tags[position]);
        tv2.setText(userInfo[position]);
        return view;

    }
}
