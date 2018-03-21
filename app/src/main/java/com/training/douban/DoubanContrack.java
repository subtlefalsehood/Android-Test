package com.training.douban;

import com.training.BasePresenter;
import com.training.BaseView;
import com.training.main.model.DoubanMovieBean;

/**
 * Created by chenqiuyi on 2018/1/19.
 */

public class DoubanContrack {
    interface View extends BaseView<Presenter> {
        void showLoading();
        void hideLoading();
        void updateView(DoubanMovieBean bean);
    }

    interface Presenter extends BasePresenter {
        void requestMovie(int start, int count);
    }
}
