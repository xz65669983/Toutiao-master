package com.meiji.elegantcommuncity.retrofit;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zhangzhengchao on 2017/12/27.
 */

public interface RetrofitImageAPI {

    @GET("elegant/files/{user_acc}portrait{uuid}.jpg")
    Observable<ResponseBody> getImageDetails(@Path("user_acc") String userAcc, @Path("uuid")String uuid );
}
