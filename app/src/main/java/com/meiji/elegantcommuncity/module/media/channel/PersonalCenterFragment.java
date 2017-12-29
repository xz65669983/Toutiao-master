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

import com.meiji.elegantcommuncity.MainActivity;
import com.meiji.elegantcommuncity.R;
import com.meiji.elegantcommuncity.Register;
import com.meiji.elegantcommuncity.activity.LoginActivity;
import com.meiji.elegantcommuncity.activity.UserDetailsActivity;
import com.meiji.elegantcommuncity.bean.media.MediaChannelBean;
import com.meiji.elegantcommuncity.constant.Fragmentid;
import com.meiji.elegantcommuncity.database.dao.MediaChannelDao;
import com.meiji.elegantcommuncity.interfaces.IOnItemLongClickListener;
import com.meiji.elegantcommuncity.retrofit.RetrofitImageAPI;
import com.meiji.elegantcommuncity.retrofit.RxRetrofit;
import com.meiji.elegantcommuncity.util.SettingUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

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

    private FragmentActivity mactivity;
    @BindView(R.id.nickname_txtv)
    public TextView tv_nickname;

    @OnClick(R.id.account_rlayout)
    public void accountManger() {
        Intent intent = new Intent(mactivity, UserDetailsActivity.class);
        startActivity(intent);
    }
    @BindView(R.id.head_mimgv)
    ImageView head_mimgv;

    @OnClick(R.id.head_mimgv)
    public void clicktouxiang() {
        Retrofit rxRetrofit = RxRetrofit.getRxRetrofitInstance();
        RetrofitImageAPI imageAPI = rxRetrofit.create   (RetrofitImageAPI.class);
        imageAPI.getImageDetails("emma", "0fdd30a6525833bb8da29829ada65329")
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
                        Log.i(TAG,"网络连接异常");
                    }
                });

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mactivity = getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_informantion, container, false);
        ButterKnife.bind(this, view);
        tv_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mactivity = (MainActivity) PersonalCenterFragment.this.mactivity;
                if (mactivity.islogin) {
                    return;
                } else {
                    Intent intent = new Intent(mactivity, LoginActivity.class);
                    startActivityForResult(intent, Fragmentid.PersonalCenterFragment);
                }

            }
        });
        return view;

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
}
