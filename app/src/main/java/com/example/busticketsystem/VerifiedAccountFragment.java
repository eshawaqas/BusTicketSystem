package com.example.busticketsystem;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

@SuppressLint("MissingInflatedId")
public class VerifiedAccountFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verified_account, container, false);
        ImageView qrCodeImageView = view.findViewById(R.id.qrCodeImageView);

        // Retrieve the QR code bitmap from the arguments
        Bundle bundle = getArguments();
        if (bundle != null) {
            Bitmap qrCodeBitmap = bundle.getParcelable("qrCode");
            if (qrCodeBitmap != null) {
                qrCodeImageView.setImageBitmap(qrCodeBitmap);
            }
        }

        return view;
    }

}
