package com.example.contactkapp.Activities.ui.qrcode;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.contactkapp.Activities.ui.qrcode.QRCodeViewModel;
import com.example.contactkapp.R;

public class QRCodeFragment extends Fragment {

    private QRCodeViewModel qrCodeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        qrCodeViewModel =
                new ViewModelProvider(this).get(QRCodeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_q_r_code, container, false);
        final TextView textView = root.findViewById(R.id.text_qrcode);
        qrCodeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}