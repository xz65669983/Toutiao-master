package com.meiji.elegantcommuncity.module.joke.content;

import com.meiji.elegantcommuncity.module.base.IBaseListView;
import com.meiji.elegantcommuncity.module.base.IBasePresenter;

/**
 * Created by Meiji on 2016/12/28.
 */

interface IJokeContent {

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
        void doLoadData();

        /**
         * 再起请求数据
         */
        void doLoadMoreData();

        /**
         * 设置适配器
         */
        void doSetAdapter();

        void doShowNoMore();
    }
}
