package com.meiji.elegantcommuncity.module.media.channel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meiji.elegantcommuncity.EventBus.LoginEvent;
import com.meiji.elegantcommuncity.EventBus.LogoutEvent;
import com.meiji.elegantcommuncity.MainActivity;
import com.meiji.elegantcommuncity.R;
import com.meiji.elegantcommuncity.Register;
import com.meiji.elegantcommuncity.activity.LoginActivity;
import com.meiji.elegantcommuncity.activity.UserDetailsActivity;
import com.meiji.elegantcommuncity.bean.media.MediaChannelBean;
import com.meiji.elegantcommuncity.constant.Fragmentid;
import com.meiji.elegantcommuncity.database.dao.MediaChannelDao;
import com.meiji.elegantcommuncity.interfaces.IOnItemLongClickListener;
import com.meiji.elegantcommuncity.model.User;
import com.meiji.elegantcommuncity.retrofit.RetrofitImageAPI;
import com.meiji.elegantcommuncity.retrofit.RxRetrofit;
import com.meiji.elegantcommuncity.util.SettingUtil;
import com.meiji.elegantcommuncity.util.UserInfoUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.MultiTypeAdapter;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

import static com.meiji.elegantcommuncity.R.id.login_password;
import static com.meiji.elegantcommuncity.R.id.recycler_view;

/**
 * Created by Meiji on 2016/12/24.
 */

public class PersonalCenterFragment extends RxFragment {

    private static final String TAG = "PersonalCenterFragment";
    private static PersonalCenterFragment instance = null;
//    private RecyclerView recyclerView;
//    private SwipeRefreshLayout swipeRefreshLayout;
//    private MultiTypeAdapter adapter;
//    private MediaChannelDao dao = new MediaChannelDao();
//    private TextView tv_desc;
//    private String isFirstTime = "isFirstTime";
//    private List<MediaChannelBean> list;

    public static PersonalCenterFragment getInstance() {
        if (instance == null) {
            instance = new PersonalCenterFragment();
        }
        return instance;
    }

    @BindView(R.id.nickname_txtv)
    public TextView tv_nickname;

    @OnClick(R.id.account_rlayout)
    public void accountManger() {
        Intent intent = new Intent(getContext(), UserDetailsActivity.class);
        startActivity(intent);
    }

    @BindView(R.id.head_mimgv)
    ImageView head_mimgv;

    @OnClick(R.id.head_mimgv)
    public void clicktouxiang() {


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //当登录完成时 页面你需要修改
        EventBus.getDefault().register(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_informantion, container, false);
        ButterKnife.bind(this, view);
        UserInfoUtil instance = UserInfoUtil.getInstance();
        String token = instance.getToken();
        if (token == null || token.contentEquals("")) {
            //未登录
            handleLogout();
        } else {
            //已登录
            hanldeLogIn();
        }

        return view;

    }

    private void handleLogout() {
        head_mimgv.setImageResource(R.mipmap.person_default_head);
        tv_nickname.setText("点击此处登录");
        tv_nickname.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void hanldeLogIn() {
        tv_nickname.setOnClickListener(null);
        tv_nickname.setText("您好:" + UserInfoUtil.getInstance().getNickName());
        getheadImage();
    }


    private void getheadImage() {
        Retrofit rxRetrofit = RxRetrofit.getRxRetrofitInstance();
        RetrofitImageAPI imageAPI = rxRetrofit.create(RetrofitImageAPI.class);
        imageAPI.getImageDetails(UserInfoUtil.getInstance().getPortraitFilename())
                .subscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, Bitmap>() {
                    @Override
                    public Bitmap apply(ResponseBody responseBody) throws Exception {
                        InputStream inputStream = responseBody.byteStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        return bitmap;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        head_mimgv.setImageBitmap(bitmap);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "网络连接异常");
                    }
                });
    }


    @Override
    public void onResume() {
        super.onResume();
        //swipeRefreshLayout.setColorSchemeColors(SettingUtil.getInstance().getColor());
        //setAdapter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (instance != null) {
            instance = null;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        hanldeLogIn();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogoutEvent(LogoutEvent logoutEvent){
        handleLogout();
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();


    }
}
