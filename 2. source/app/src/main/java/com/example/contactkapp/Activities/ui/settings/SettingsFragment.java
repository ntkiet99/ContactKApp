package com.example.contactkapp.Activities.ui.settings;

import androidx.cardview.widget.CardView;
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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.contactkapp.Activities.AccountActivity;
import com.example.contactkapp.Activities.LoginActivity;
import com.example.contactkapp.Activities.MainActivity;
import com.example.contactkapp.Activities.RegisterActivity;
import com.example.contactkapp.Activities.Update_Item_Activity;
import com.example.contactkapp.Activities.ui.qrcode.QRCodeViewModel;
import com.example.contactkapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private CardView btn_account, btn_item, btn_logout;
    private Intent AccountActivity;

    private FirebaseAuth mAuth;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        btn_account = root.findViewById(R.id.card_profile_account);
        btn_item = root.findViewById(R.id.card_profile_item);
        btn_logout = root.findViewById(R.id.card_profile_logout);

        mAuth = FirebaseAuth.getInstance();
        // events button
        btn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountActivity = new Intent(getActivity(), AccountActivity.class);
                startActivity(AccountActivity);
            }
        });

        btn_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountActivity = new Intent(getActivity(), Update_Item_Activity.class);
                startActivity(AccountActivity);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Bạn muốn đăng xuất tài khoản?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LogoutAccount();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return root;
    }

    private  void LogoutAccount(){
        mAuth.signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

}