package com.meiji.elegantcommuncity.module.joke.comment;

import com.meiji.elegantcommuncity.bean.joke.JokeCommentBean;
import com.meiji.elegantcommuncity.module.base.IBaseListView;
import com.meiji.elegantcommuncity.module.base.IBasePresenter;

import java.util.List;

/**
 * Created by Meiji on 2017/1/1.
 */

interface IJokeComment {

    interface View extends IBaseListView<Presenter> {

        /**
         * 请求数据
         */
        void onLoadData();
    }

    interface Presenter extends IBasePresenter {

        /**
         * 请求数据
         */
        void doLoadData(String... jokeId_Count);

        /**
         * 再起请求数据
         */
        void doLoadMoreData();

        /**
         * 设置适配器
         */
        void doSetAdapter(List<JokeCommentBean.DataBean.RecentCommentsBean> commentsBeanList);

        /**
         * 加载完毕
         */
        void doShowNoMore();
    }
}
