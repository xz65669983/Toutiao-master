package com.meiji.elegantcommuncity.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.meiji.elegantcommuncity.EventBus.LoginEvent;
import com.meiji.elegantcommuncity.R;
import com.meiji.elegantcommuncity.RxBus;
import com.meiji.elegantcommuncity.model.User;
import com.meiji.elegantcommuncity.model.UserModel;
import com.meiji.elegantcommuncity.model.login.LoginResponse;
import com.meiji.elegantcommuncity.retrofit.MyRetrofit;
import com.meiji.elegantcommuncity.retrofit.UserService;
import com.meiji.elegantcommuncity.util.UserInfoUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.Result;

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
    public void login() {

        Retrofit retrofit = MyRetrofit.getGsonRetrofitInstance();
        UserService userService = retrofit.create(UserService.class);
        User user = new User();
        user.setUserAcc(login_phone.getText().toString());
        user.setPwd(login_password.getText().toString());
        UserModel userModel = new UserModel();
        userModel.setUser(user);
        Call<LoginResponse> call = userService.login(userModel);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse body = response.body();

                if (body.getResultCode().contentEquals("0000")) {
                    //登录成功
                    UserInfoUtil instance = UserInfoUtil.getInstance();
                    //保存Token
                    instance.saveToken(body.getToken());
                    //保存
                    User user = body.getUser();
                    instance.saveUserName(user.getUserName());
                    instance.saveEmail(user.getEmail());
                    instance.saveMale(user.getMale());
                    instance.saveNickName(user.getNickName());
                    instance.savePhoneNumber( user.getPhoneNumber());
                    instance.savePwd( user.getPwd());
                    instance.saveUserAcc( user.getUserAcc());
                    instance.saveUserGrade(user.getUserGrade());
                    instance.saveVerifyType(user.getVerifyType());
                    instance.savepPortraitFilename(user.getPortraitFilename());
                    LoginEvent loginEvent = new LoginEvent();
                    loginEvent.setUser(user);
                    EventBus.getDefault().post(loginEvent);
                    Toast.makeText(LoginActivity.this,"登录成功，欢迎回来："+instance.getNickName(),Toast.LENGTH_LONG).show();
                    finish();


                } else {
                    AlertDialog.Builder customizeDialog =
                            new AlertDialog.Builder(LoginActivity.this);

                    customizeDialog.setTitle("登录失败")
                            .setMessage(body.getResultMessage())
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();
                }
                String token = body.getToken();
                Log.i(TAG, token);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "亲，网络异常，请稍后再试", Toast.LENGTH_LONG).show();
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
