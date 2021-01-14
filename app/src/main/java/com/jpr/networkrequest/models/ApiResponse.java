package com.jpr.networkrequest.models;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("result_code")
    private int resultCode;

    @SerializedName("name")
    private String name;

    @SerializedName("username")
    private String username;

    public String getStatus() {
        return status;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getName() {
        return name;
    }

    public String getUsername(){
        return username;
    }
}
