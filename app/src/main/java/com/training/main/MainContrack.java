package com.training.main;

import com.training.BasePresenter;
import com.training.BaseView;

import java.util.List;

/**
 * Created by chenqiuyi on 2017/12/25.
 */

public interface MainContrack {
    interface View extends BaseView<Presenter> {
        void setItems(List<MainItemBean> items);
    }

    interface Presenter extends BasePresenter {

    }
}
