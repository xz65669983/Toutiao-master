package com.meiji.elegantcommuncity.retrofit;


import com.meiji.elegantcommuncity.model.MyResponseBody;
import com.meiji.elegantcommuncity.model.issueactivity.IssueActivityModel;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/12/31.
 */

public interface IssueActivityService {

    @POST("elegant/activity/publishActivity")
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    //Observable<ResponseBody> issueActivity(@Body IssueActivityModel issueActivityModel);
    Observable<MyResponseBody> issueActivity(@Body IssueActivityModel issueActivityModel);
}
