package com.android.mb.wash.view.interfaces;

import com.android.mb.wash.base.BaseMvpView;
import com.android.mb.wash.entity.HomeData;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IHomeView extends BaseMvpView {
    void getHomeData(HomeData homeData);
}
