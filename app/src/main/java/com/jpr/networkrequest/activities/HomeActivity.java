package com.jpr.networkrequest.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jpr.networkrequest.R;
import com.jpr.networkrequest.apputil.AppConfig;
import com.jpr.networkrequest.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding homeBinding;
    private AppConfig appConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        String getName = getIntent().getStringExtra("name");
        String getUsername = getIntent().getStringExtra("username");
        appConfig = new AppConfig(this);
        homeBinding.tvName.setText("Welcome " + getName);
        homeBinding.tvUsername.setText(getUsername);
        homeBinding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                appConfig.updateUserLoginStatus(false);
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                finish();

            }
        });
    }
}