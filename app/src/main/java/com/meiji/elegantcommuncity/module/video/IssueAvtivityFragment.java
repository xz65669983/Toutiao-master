package com.meiji.elegantcommuncity.module.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.meiji.elegantcommuncity.R;
import org.feezu.liuli.timeselector.TimeSelector;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
/**
 * Created by zhangzhengchao on 2017/12/25.
 */
public class IssueAvtivityFragment extends Fragment {


    @BindView(R.id.start_time)
    TextView tvStartTime;
    @OnClick(R.id.start_time)
    public void startTime(){
      pickTime(tvStartTime);
    }

    @BindView(R.id.end_time)
    TextView tvEndTime;
    @OnClick(R.id.end_time)
    public void endTime(){
        pickTime(tvEndTime);
    }

    @BindView(R.id.tv_select_location)
    TextView tvSelectLoacation;
    @OnClick(R.id.issue_activity_place)
    public void selectLocation(){
        CityPickerView.getInstance().setConfig(new CityConfig.Builder(this.getContext()).build());
        CityPickerView.getInstance().setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                tvSelectLoacation.setText(province.toString()+city.toString()+district.toString());
            }

            @Override
            public void onCancel() {
                ToastUtils.showLongToast(IssueAvtivityFragment.this.getContext(), "已取消");
            }
        });

        //显示
        CityPickerView.getInstance().showCityPicker(this.getContext());

    }

    @BindView(R.id.tv_end_activity_time)
    TextView tvEndActivityTime;

    @OnClick(R.id.issue_activity_sign_up_end)
    public void issueActivitySignUpEnd(){
        pickTime(tvEndActivityTime);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_issue_activity, container, false);
        ButterKnife.bind(this,view);

        return view;
    }

    public void pickTime(final TextView view){
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(final ObservableEmitter<String> e) throws Exception {
                TimeSelector timeSelector = new TimeSelector(IssueAvtivityFragment.this.getContext(), new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        e.onNext(time);
                    }
                }, "2017-12-01 00:00", "2025-12-31 00:00");
                timeSelector.show();
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                view.setText(s);
            }
        });
    }
}
