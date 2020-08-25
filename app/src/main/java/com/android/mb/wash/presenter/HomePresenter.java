package com.android.mb.wash.presenter;

import android.text.TextUtils;

import com.android.mb.wash.base.BaseMvpPresenter;
import com.android.mb.wash.entity.HomeData;
import com.android.mb.wash.presenter.interfaces.IHomePresenter;
import com.android.mb.wash.service.ScheduleMethods;
import com.android.mb.wash.view.interfaces.IHomeView;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class HomePresenter extends BaseMvpPresenter<IHomeView> implements IHomePresenter {


    @Override
    public void getHomeData() {
        Observable observable = ScheduleMethods.getInstance().getHomeData();
        toSubscribe(observable,  new Subscriber<HomeData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(mMvpView!=null && !TextUtils.isEmpty(e.getMessage())){
                    mMvpView.showToastMessage(e.getMessage());
                }
            }

            @Override
            public void onNext(HomeData result) {
                if (mMvpView!=null){
                    mMvpView.getHomeData(result);
                }
            }
        });
    }
}
