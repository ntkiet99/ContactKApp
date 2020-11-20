package com.example.contactkapp.Activities.ui.qrcode;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contactkapp.Activities.AccountActivity;
import com.example.contactkapp.Activities.ContactDetailActivity;
import com.example.contactkapp.Activities.MainActivity;
import com.example.contactkapp.Activities.ScanQRActivity;
import com.example.contactkapp.Activities.ui.qrcode.QRCodeViewModel;
import com.example.contactkapp.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QRCodeFragment extends Fragment {

    private QRCodeViewModel qrCodeViewModel;
    private Button btnScan;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        qrCodeViewModel =
                new ViewModelProvider(this).get(QRCodeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_q_r_code, container, false);

        btnScan = root.findViewById(R.id.btn_open_camera);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator =  IntentIntegrator.forSupportFragment(QRCodeFragment.this);
                intentIntegrator.setPrompt("For flash use volume up key");
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(requestCode == IntentIntegrator.REQUEST_CODE) {
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(getActivity(), ContactDetailActivity.class));
                    Log.d("ScanFrag", "Scanned | " + result.getContents());
                    Toast.makeText(getContext(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}