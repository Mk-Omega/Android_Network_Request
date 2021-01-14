package com.jpr.networkrequest.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.google.android.material.snackbar.Snackbar;
import com.jpr.networkrequest.R;
import com.jpr.networkrequest.apputil.AppConfig;
import com.jpr.networkrequest.databinding.ActivityMainBinding;
import com.jpr.networkrequest.models.ApiResponse;
import com.jpr.networkrequest.retrofitutil.ApiClient;
import com.jpr.networkrequest.retrofitutil.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;

    private boolean isRememberUserLogin = false;
    private AppConfig appConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(activityMainBinding.myToolbar);
        getSupportActionBar().setTitle("Login");
        appConfig = new AppConfig(this);

        if(appConfig.isUserLogin()){
            String name = appConfig.getNameInfo();
            String username = appConfig.getUsernameInfo();

            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("username", username);
            startActivity(intent);
            finish();
        }

        activityMainBinding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        activityMainBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if(activityMainBinding.email.getText().toString().equals("") || activityMainBinding.pass.getText().toString().equals(""))

                login();
                activityMainBinding.showProgress.setVisibility(View.VISIBLE);

            }
        });

    }

    private void login(){
        String email = activityMainBinding.email.getText().toString();
        String pass = activityMainBinding.pass.getText().toString();

        Call<ApiResponse> apiResponseCall = ApiClient.getApiClient().create(ApiInterface.class).performUserLogin(email, pass);
        apiResponseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.code()==200){
                    if(response.body().getStatus().equals("ok")){
                        if(response.body().getResultCode()==1){
                            String name = response.body().getName();
                            String username = response.body().getUsername();
                            if(isRememberUserLogin){
                                appConfig.updateUserLoginStatus(true);
                                appConfig.saveUserInfo(name,username);

                            }
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            intent.putExtra("name", name);
                            intent.putExtra("username", username);
                            startActivity(intent);
                            finish();
                        }else{
                            displayUserInfo("Login failed. User not found");
                        }
                    }else{
                        displayUserInfo("Something went wrong");
                    }
                }else{
                    displayUserInfo("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
    }

    private void displayUserInfo(String message){
        Snackbar.make(activityMainBinding.myConstraintLayoutLogin, message, Snackbar.LENGTH_SHORT).show();
        activityMainBinding.showProgress.setVisibility(View.INVISIBLE);
    }

    public void checkBoxClick(View view){
        isRememberUserLogin = ((CheckBox) view).isChecked();
    }
}