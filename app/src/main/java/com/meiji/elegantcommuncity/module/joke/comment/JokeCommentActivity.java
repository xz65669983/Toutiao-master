package com.meiji.elegantcommuncity.module.joke.comment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.meiji.elegantcommuncity.InitApp;
import com.meiji.elegantcommuncity.R;
import com.meiji.elegantcommuncity.bean.joke.JokeContentBean;
import com.meiji.elegantcommuncity.module.base.BaseActivity;

/**
 * Created by Meiji on 2017/1/1.
 */

public class JokeCommentActivity extends BaseActivity {

    private static final String TAG = "NewsCommentView";

    public static void launch(JokeContentBean.DataBean.GroupBean bean) {
        InitApp.AppContext.startActivity(new Intent(InitApp.AppContext, JokeCommentActivity.class)
                .putExtra(TAG, bean)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, JokeCommentFragment.newInstance(getIntent().getParcelableExtra(TAG)))
                .commit();
    }
}
