package com.meiji.elegantcommuncity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.meiji.elegantcommuncity.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

//        Retrofit retrofit = MyRetrofit.getGsonRetrofitInstance();
//        UserService userService = retrofit.create(UserService.class);
//
//
////        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),s);
//        Call<ResponseBody> call = userService.login(body);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    Log.i(TAG,"接收到的信息为："+response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.i(TAG,"连接服务器失败");
//
//            }
//        });


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
}
