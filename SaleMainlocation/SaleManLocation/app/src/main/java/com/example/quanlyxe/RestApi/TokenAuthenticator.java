package com.example.quanlyxe.RestApi;


import androidx.annotation.Nullable;


import com.example.quanlyxe.MainActivity;
import com.example.quanlyxe.login.LoginActivity;
import com.example.quanlyxe.login.plashActivity;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {
    String accessToken="";
    int responcode=0;
    @Nullable
    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        String userRefreshToken = plashActivity._objpref.getString("refresh_token","");
        String cid = LoginActivity.username;
        String password = LoginActivity.pass;
        String baseUrl = "https://laixekg.kgc.edu.vn/";

        boolean refreshResult = refreshToken(baseUrl, userRefreshToken, cid);
        if (refreshResult) {
            //token moi cua ban day
             //accessToken = "your new access token";

            // thuc hien request hien tai khi da lay duoc token moi
            return response.request().newBuilder().header("Authorization","Bearer "+ accessToken).build();
        } else {
            if (responcode==400){
                boolean refreshResult_lan2 = refreshResult_lan2(baseUrl, password, cid);
                if (refreshResult_lan2){
                    return response.request().newBuilder().header("Authorization","Bearer "+ accessToken).build();

                }
                else {
                    return  null;
                }
            }
            //Khi refresh token failed ban co the thuc hien action refresh lan tiep theo
            return null;
        }
    }

    private boolean refreshResult_lan2(String baseUrl, String password, String cid) throws IOException {

            URL refreshUrl = new URL(baseUrl + "oauth/token");
            HttpURLConnection urlConnection = (HttpURLConnection) refreshUrl.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setUseCaches(false);
            String urlParameters = "grant_type=password&username="
                    + cid
                    + "&password="
                    + password;

            urlConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = urlConnection.getResponseCode();

            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                LoginActivity.editor = LoginActivity.sharedPreferences.edit();
                // SharedPreferences.Editor editor=HomeActivity.sharedPreferences.edit();
                // this gson part is optional , you can read response directly from Json too
                Gson gson = new Gson();
                tokenReponse tokenReponse = gson.fromJson(response.toString(), tokenReponse.class);
                LoginActivity.editor.putString("token", tokenReponse.getAccess_token());
                LoginActivity.editor.putString("refresh_token", tokenReponse.getRefresh_token());
                accessToken = tokenReponse.getAccess_token();
                LoginActivity.editor.commit();

                return true;
            } else {
                return false;
            }

    }

    public boolean refreshToken(String url, String refresh, String cid )
            throws IOException {
        URL refreshUrl = new URL(url + "oauth/token");
        HttpURLConnection urlConnection = (HttpURLConnection) refreshUrl.openConnection();
        urlConnection.setDoInput(true);
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        urlConnection.setUseCaches(false);
        String urlParameters = "grant_type=refresh_token&client_id="
                + cid
                + "&refresh_token="
                + refresh;

        urlConnection.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = urlConnection.getResponseCode();

        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            LoginActivity.editor= LoginActivity.sharedPreferences.edit();
           // SharedPreferences.Editor editor=HomeActivity.sharedPreferences.edit();
            // this gson part is optional , you can read response directly from Json too
            Gson gson = new Gson();
            tokenReponse tokenReponse =gson.fromJson(response.toString(), tokenReponse.class);
            LoginActivity.editor.putString("token",tokenReponse.getAccess_token());
            LoginActivity.editor.putString("refresh_token",tokenReponse.getRefresh_token());
            accessToken=tokenReponse.getAccess_token();
            LoginActivity.editor.commit();
            // handle new token ...
            // save it to the sharedpreferences, storage bla bla ...
            return true;
        } else {
            responcode=responseCode;
            //cannot refresh
            return false;
        }

    }

}
