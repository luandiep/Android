package com.example.quanlyxe.RestApi;

import com.google.gson.annotations.SerializedName;

public class tokenReponse {
    @SerializedName("access_token")
    private String access_token;

    @SerializedName("refresh_token")
    private String refresh_token;



    public  tokenReponse(){

    }


    public  void setAccess_token(String access_token){this.access_token=access_token;}
    public  void setToken_type(String refresh_token){this.refresh_token=refresh_token;}


    public String getAccess_token(){ return  access_token;}
    public String getRefresh_token(){ return  refresh_token;}

}









