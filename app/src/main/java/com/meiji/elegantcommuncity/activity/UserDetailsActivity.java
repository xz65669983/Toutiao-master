package com.meiji.elegantcommuncity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.meiji.elegantcommuncity.R;
import com.meiji.elegantcommuncity.model.User;
import com.meiji.elegantcommuncity.retrofit.MyRetrofit;
import com.meiji.elegantcommuncity.retrofit.UserService;
import com.meiji.elegantcommuncity.util.UserInfoUtil;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/7/30.
 */

public class UserDetailsActivity extends AppCompatActivity {
    private static final String TAG = "UserDetailsActivity";

    @OnClick(R.id.revise_password_txt)
    public void revisePassword(){
        Intent intent =new Intent(UserDetailsActivity.this,RevisePasswordActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.btn_save)
    public void save(){
        finish();
    }
    @OnClick(R.id.btn_logout)
        public void logout(){
        Retrofit retrofit = MyRetrofit.getGsonRetrofitInstance();
        UserService userService = retrofit.create(UserService.class);

        User user=new User();
        String userName = UserInfoUtil.getInstance().getUserName();
        user.setUserName(userName);
        Call<ResponseBody> call = userService.logOut(user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String respond = response.body().string();
                    JsonElement je = new JsonParser().parse(respond);
                    String resultCode = je.getAsJsonObject().get("resultCode").toString();
                    if("0000".equals(resultCode)){
                        Log.i(TAG,"登出成功");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(UserDetailsActivity.this, "链接失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spuser_details);
        ButterKnife.bind(this);

        Observable<Object> observable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {

            }
        });



    }
}
