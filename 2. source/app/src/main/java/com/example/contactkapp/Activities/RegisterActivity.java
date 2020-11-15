package com.example.contactkapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.contactkapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {

    private EditText userName, userEmail, userPassword, userConfirmPassword;
    private Button regBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.register_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        userName = findViewById(R.id.txt_register_username);
        userEmail = findViewById(R.id.txt_register_email);
        userPassword = findViewById(R.id.txt_register_password);
        userConfirmPassword = findViewById(R.id.txt_register_confirm_password);
        regBtn = findViewById(R.id.btn_create_new_account);

        // firebase
        mAuth = FirebaseAuth.getInstance();

        // click create new account
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = userName.getText().toString();
                final String email = userEmail.getText().toString();
                final String password = userPassword.getText().toString();
                final String passwordConfirm = userConfirmPassword.getText().toString();
                if(username.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()){
                    ShowMessage("Lỗi! Kiểm tra lại dữ liệu của bạn!");
                }else if(!password.equals(passwordConfirm)){
                    ShowMessage("Mật khẩu bạn nhập không đồng nhất!");
                }
                else{
                    CreateUserAccount(username, email,password);
                }
            }
        });
    }

    private void CreateUserAccount(String username, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    ShowMessage("Đăng ký tài khoản thành công!");
                    UpdateUserInfo(username,  mAuth.getCurrentUser());
                }else {
                    ShowMessage("Đăng ký tài khoản thất bại!" + task.getException().getMessage());
                }
            }
        });
    }

    private void UpdateUserInfo(String username, FirebaseUser currentUser) {
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
        currentUser.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    ShowMessage("Thiết lập tài khoản hoàn tất!");
                    updateUI();
                }
            }
        });
    }

    private void updateUI() {
        Intent home = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(home);
        finish();
    }

    private void hideActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_register);
    }

    private  void ShowMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}