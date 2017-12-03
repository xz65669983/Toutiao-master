package com.meiji.elegantcommuncity.module.wenda.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.meiji.elegantcommuncity.InitApp;
import com.meiji.elegantcommuncity.R;
import com.meiji.elegantcommuncity.module.base.BaseActivity;

/**
 * Created by Meiji on 2017/5/22.
 */

public class WendaContentActivity extends BaseActivity {

    private static final String TAG = "WendaContentActivity";

    public static void launch(String qid) {
        InitApp.AppContext.startActivity(new Intent(InitApp.AppContext, WendaContentActivity.class)
                .putExtra(TAG, qid)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, WendaContentFragment.newInstance(getIntent().getStringExtra(TAG)))
                .commit();
    }
}
