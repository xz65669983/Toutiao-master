package com.meiji.elegantcommuncity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.meiji.elegantcommuncity.R;
import com.meiji.elegantcommuncity.model.User;
import com.meiji.elegantcommuncity.retrofit.MyRetrofit;
import com.meiji.elegantcommuncity.retrofit.UserService;
import com.meiji.elegantcommuncity.util.UserInfoUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by zzc on 2017/6/18.
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity ";
    @BindView(R.id.login_phone)
    EditText login_phone;
    @BindView(R.id.login_password)
    EditText login_password;

    @OnClick(R.id.forget_password)
    public void forgetPassword() {
        Intent intent = new Intent(this, FindPasswordActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_register)
    public void signUp() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_back)
    public void backUp() {
        finish();
    }

    @OnClick(R.id.btn_login)
    public void login()  {

        Retrofit retrofit = MyRetrofit.getGsonRetrofitInstance();
        UserService userService = retrofit.create(UserService.class);
        User user=new User();
        user.setUserAcc(login_phone.getText().toString());
        user.setPwd(login_password.getText().toString());
        Call<ResponseBody> call = userService.login(user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String respond=response.body().string();
                    Log.i(TAG,"接收到的信息为："+respond);
                    JsonElement je = new JsonParser().parse(respond);
                    String resultCode = je.getAsJsonObject().get("resultCode").toString();
                    if(resultCode.contentEquals("0000")){
                        //登录成功
                        String token = je.getAsJsonObject().get("token").toString();

                        //保存Token
                        UserInfoUtil.getInstance().saveToken(token);
                        //保存用户名
                        UserInfoUtil.getInstance().saveUserName(login_phone.getText().toString());

                        finish();

                    }else {
                        //登陆失败
                        String resultMessage = je.getAsJsonObject().get("resultMessage").toString();
                        Toast.makeText(LoginActivity.this, "登陆失败,"+resultMessage, Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG,"连接服务器失败");

            }
        });


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
}
