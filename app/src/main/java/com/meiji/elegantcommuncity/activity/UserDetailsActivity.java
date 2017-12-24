package com.meiji.elegantcommuncity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.meiji.elegantcommuncity.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by Administrator on 2017/7/30.
 */

public class UserDetailsActivity extends AppCompatActivity {
    @OnClick(R.id.revise_password_txt)
    public void revisePassword(){
        Intent intent =new Intent(UserDetailsActivity.this,RevisePasswordActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.btn_save)
    public void save(){
        finish();
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
