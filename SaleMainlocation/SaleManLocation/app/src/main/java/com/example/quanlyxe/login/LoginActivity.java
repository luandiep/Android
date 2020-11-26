package com.example.quanlyxe.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.quanlyxe.GoogleMaps;
import com.example.quanlyxe.MainActivity;
import com.example.quanlyxe.R;
import com.example.quanlyxe.RestApi.ApiCient;
import com.example.quanlyxe.RestApi.ApiService;
import com.example.quanlyxe.RestApi.tokenReponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public  static  SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    EditText Edit_username,Edit_password;
    CheckBox cbcheckBox;
    RelativeLayout summit;


    public static  String token="";
    public static String refreshtoken="";
    public  static String username = "";
    public  static String pass = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences=getSharedPreferences("Data",MODE_PRIVATE);
        Edit_username=findViewById(R.id.username);
        Edit_password=findViewById(R.id.password);
        summit=findViewById(R.id.login);
        cbcheckBox=(CheckBox)findViewById(R.id.checkBox);
        Edit_username.setText(sharedPreferences.getString("taikhoans",""));
        Edit_password.setText(sharedPreferences.getString("matkhaus",""));
        cbcheckBox.setChecked(sharedPreferences.getBoolean("checked",false));
        summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Callloginservice();
            }
        });
    }
    private void Callloginservice() {
        try {
            username = Edit_username.getText().toString().trim();
            pass = Edit_password.getText().toString().trim();
            ApiService service = ApiCient.getClient().create(ApiService.class);
            Call<ResponseBody> srvLogin = service.getToken("password", username, pass);
            srvLogin.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){

                        try {
                            String ResponJson=response.body().string();
                            Gson objGson =new Gson();
                            tokenReponse objResp=objGson.fromJson(ResponJson, tokenReponse.class);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("token",objResp.getAccess_token());
                            editor.putString("refresh_token",objResp.getRefresh_token());
                            token=objResp.getAccess_token();
                            refreshtoken=objResp.getRefresh_token();
                            editor.commit();

                            if (token!=null){
                                Intent intent=new Intent(getApplicationContext(), GoogleMaps.class);
                                intent.putExtra("username", username);
                                startActivity(intent);
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công" , Toast.LENGTH_SHORT).show();
                            }
                            if (cbcheckBox.isChecked()){

                                editor.putString("taikhoans",username);
                                editor.putString("matkhaus",pass);
                                editor.putBoolean("checked",true);
                                editor.commit();
                            }else {

                                editor.remove("taikhoans");
                                editor.remove("matkhaus");
                                editor.remove("checked");
                                editor.commit();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }else {

                        Toast.makeText(LoginActivity.this,"Sai mật khẩu",Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Lấy token thất bại" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, e.getMessage() , Toast.LENGTH_SHORT).show();
            }



    }
}