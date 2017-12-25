package com.meiji.elegantcommuncity.module.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meiji.elegantcommuncity.R;

/**
 * Created by zhangzhengchao on 2017/12/25.
 */

public class IssueAvtivityFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_issue_activity, container, false);
        return view;
    }
}
