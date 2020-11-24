package com.example.contactkapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.contactkapp.Models.UserModel;
import com.example.contactkapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ContactDetailActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    String strAvatar;
    private String qrCode = "", userKey = null;
    private Button btn_detail_following;
    private ImageView img_Avatar;
    private TextView detail_name, detail_email, content_phone, content_gmail ,content_zalo , content_facebook,content_instagram ,content_tiktok ,content_twitter ,content_wechat ,content_github ;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        getSupportActionBar().hide();
        findViewById(R.id.imageBack_qrcodescan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        img_Avatar = findViewById(R.id.detail_avatar);
        detail_name = findViewById(R.id.detail_name);
        detail_email = findViewById(R.id.detail_email);
        content_phone = findViewById(R.id.info_content_phone);
        content_gmail = findViewById(R.id.info_content_gmail);
        content_zalo = findViewById(R.id.info_content_zalo);
        content_facebook = findViewById(R.id.info_content_facebook);
        content_instagram = findViewById(R.id.info_content_instagram);
        content_tiktok = findViewById(R.id.info_content_tiktok);
        content_twitter = findViewById(R.id.info_content_twitter);
        content_wechat = findViewById(R.id.info_content_wechat);
        content_github = findViewById(R.id.info_content_github);
        btn_detail_following = findViewById(R.id.btn_detail_following);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        userKey = getIntent().getExtras().getString("userKey");
        if(userKey != null){
            String qrcode = getIntent().getExtras().getString("qrcode");
            LoadUserItem(qrcode);
            userKey = null;
        }else{
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                qrCode = extras.getString("data");
                if(!qrCode.isEmpty()){
                    LoadUserItem(qrCode);
                }
            }
        }

        btn_detail_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_detail_following.getText().toString().equals("Theo dõi")){
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(currentUser.getUid())
                            .child("Following").child(user.getUserKey()).setValue(user.getUserKey());
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getUserKey())
                            .child("Followers").child(currentUser.getUid()).setValue(currentUser.getUid());
                    Toast.makeText(ContactDetailActivity.this, "Đã theo dõi!", Toast.LENGTH_SHORT).show();

                }else{
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(currentUser.getUid())
                            .child("Following").child(user.getUserKey()).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getUserKey())
                            .child("Followers").child(currentUser.getUid()).removeValue();
                    Toast.makeText(ContactDetailActivity.this, "Bỏ theo dõi!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void LoadUserItem(String qrcodeResult){
        DatabaseReference userItem = FirebaseDatabase.getInstance().getReference().child("UserItem");
        Query updateUserItemQuery = userItem.orderByChild("qrcode").equalTo(qrcodeResult);
        updateUserItemQuery.keepSynced(false);
        updateUserItemQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot broccoli: snapshot.getChildren()){
                    user = broccoli.getValue(UserModel.class);
                    isFollowing(user.getUserKey(), btn_detail_following);
                    detail_name.setText(broccoli.child("displayName").getValue(String.class));
                    detail_email.setText(broccoli.child("userName").getValue(String.class));
                    content_phone.setText( broccoli.child("phone").getValue(String.class));
                    content_gmail.setText( broccoli.child("gmail").getValue(String.class));
                    content_zalo.setText( broccoli.child("zalo").getValue(String.class));
                    content_facebook.setText( broccoli.child("facebook").getValue(String.class));
                    content_instagram.setText( broccoli.child("instagram").getValue(String.class));
                    content_tiktok.setText( broccoli.child("tiktok").getValue(String.class));
                    content_twitter.setText( broccoli.child("twitter").getValue(String.class));
                    content_wechat.setText( broccoli.child("wechat").getValue(String.class));
                    content_github.setText( broccoli.child("github").getValue(String.class));
                    strAvatar = broccoli.child("avatar").getValue(String.class);

                    Glide.with(getApplicationContext()).load(strAvatar).into(img_Avatar);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void isFollowing(final  String userid, final  Button button){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(currentUser.getUid()).child("Following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(userid).exists()){
                    button.setText("Đang theo dõi");

                }else{
                    button.setText("Theo dõi");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}