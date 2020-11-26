package com.example.quanlyxe.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.quanlyxe.GoogleMaps;
import com.example.quanlyxe.MainActivity;
import com.example.quanlyxe.R;
import com.example.quanlyxe.service.MyService;

public class plashActivity extends AppCompatActivity {
    public  static SharedPreferences _objpref;
    public static SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plash);
        _objpref=getSharedPreferences("Data",MODE_PRIVATE);
        Thread thread=new Thread(){
            @Override
            public  void run(){

                try {
                    sleep(10000);
                    startService(new Intent(getBaseContext(), MyService.class));
                    actUserLogin();

                    finish();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();

    }

    private void actUserLogin() {

        _objpref=getSharedPreferences("Data",MODE_PRIVATE);

            if (_objpref.getString("token","")!=""){
            Intent intent=new Intent(getApplicationContext(), GoogleMaps.class);
            startActivity(intent);
        }
        else {
            Intent login =new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(login);
        }

    }
}