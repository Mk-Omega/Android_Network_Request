package com.jpr.networkrequest.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.jpr.networkrequest.R;
import com.jpr.networkrequest.databinding.ActivityMainBinding;
import com.jpr.networkrequest.databinding.ActivityRegisterBinding;
import com.jpr.networkrequest.models.ApiResponse;
import com.jpr.networkrequest.retrofitutil.ApiClient;
import com.jpr.networkrequest.retrofitutil.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding activityRegisterBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_register);
        if (android.os.Build.VERSION.SDK_INT >= 19)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        setSupportActionBar(activityRegisterBinding.myToolbar);
        getSupportActionBar().setTitle("Register");

        activityRegisterBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signUp();
                activityRegisterBinding.showProgress.setVisibility(View.VISIBLE);

            }
        });

    }

    private void signUp(){

        String name = activityRegisterBinding.name.getText().toString();
        String uname = activityRegisterBinding.username.getText().toString();
        String email = activityRegisterBinding.email.getText().toString();
        String password = activityRegisterBinding.pass.getText().toString();

        Call<ApiResponse> apiResponseCall = ApiClient.getApiClient().create(ApiInterface.class).performUserSignIn(name, uname, email, password);
        apiResponseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                if(response.code()==200){
                    if(response.body().getStatus().equals("ok")){
                        if(response.body().getResultCode()==1){
                            Toast.makeText(RegisterActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            finish();
                        }else{
                            displayUserInfo("User already exist.");
                            activityRegisterBinding.name.setText("");
                            activityRegisterBinding.username.setText("");
                            activityRegisterBinding.email.setText("");
                            activityRegisterBinding.pass.setText("");
                            activityRegisterBinding.conpass.setText("");
                        }
                    }else{
                        displayUserInfo("Something went wrong.");
                        activityRegisterBinding.name.setText("");
                        activityRegisterBinding.username.setText("");
                        activityRegisterBinding.email.setText("");
                        activityRegisterBinding.pass.setText("");
                        activityRegisterBinding.conpass.setText("");
                    }
                }else{
                    displayUserInfo("Something went wrong.");
                    activityRegisterBinding.name.setText("");
                    activityRegisterBinding.username.setText("");
                    activityRegisterBinding.email.setText("");
                    activityRegisterBinding.pass.setText("");
                    activityRegisterBinding.conpass.setText("");
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });

    }

    private void displayUserInfo(String message){
        Snackbar.make(activityRegisterBinding.myConstraintLayout, message, Snackbar.LENGTH_SHORT).show();
        activityRegisterBinding.name.setText("");
        activityRegisterBinding.username.setText("");
        activityRegisterBinding.email.setText("");
        activityRegisterBinding.pass.setText("");
        activityRegisterBinding.conpass.setText("");
        activityRegisterBinding.showProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}