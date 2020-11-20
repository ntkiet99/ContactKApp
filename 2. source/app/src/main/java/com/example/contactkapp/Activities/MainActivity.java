package com.example.contactkapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.contactkapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.toolbar);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_contact, R.id.navigation_profile, R.id.navigation_qrcode, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // This is important, otherwise the result will not be passed to the fragment
        super.onActivityResult(requestCode, resultCode, data);
    }

    private  long backPressTime;
    @Override
    public  void onBackPressed(){

        if (backPressTime + 2000 > System.currentTimeMillis()){
            this.finishAffinity();
            System.exit(1);
            return;
        }else{
            Toast.makeText(getBaseContext(),"Bạn muốn thoát khỏi ứng dụng!",Toast.LENGTH_LONG).show();
        }
        backPressTime = System.currentTimeMillis();
    }
}