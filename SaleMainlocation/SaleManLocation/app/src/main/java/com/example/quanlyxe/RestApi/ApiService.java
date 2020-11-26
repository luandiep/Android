package com.example.quanlyxe.RestApi;

import com.example.quanlyxe.Viewmodel.ViTriDiaLy;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded

    @POST("oauth/token")
            @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded",

    })
    Call<ResponseBody> getToken(@Field("grant_type") String grant_type,
                                @Field("username") String name,
                                @Field("password") String password
    );

    @POST("/api/vitridialy/create")

    Call<ResponseBody> UpdateViTRi(@Header("Authorization") String Authorization,
                                   @Body ViTriDiaLy viTriDiaLy

                                   );





}
