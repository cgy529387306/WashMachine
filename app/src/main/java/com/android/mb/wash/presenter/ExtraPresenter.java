package com.android.mb.wash.presenter;

import android.text.TextUtils;

import com.android.mb.wash.base.BaseMvpPresenter;
import com.android.mb.wash.entity.CountData;
import com.android.mb.wash.entity.QQBean;
import com.android.mb.wash.entity.VersionBean;
import com.android.mb.wash.presenter.interfaces.IExtraPresenter;
import com.android.mb.wash.service.ScheduleMethods;
import com.android.mb.wash.view.interfaces.IExtraView;

import rx.Observable;
import rx.Subscriber;

public class ExtraPresenter extends BaseMvpPresenter<IExtraView> implements IExtraPresenter{

    @Override
    public void getCountData() {
        Observable observable = ScheduleMethods.getInstance().getCountData();
        toSubscribe(observable,  new Subscriber<CountData>() {
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
            public void onNext(CountData result) {
                if (mMvpView!=null){
                    mMvpView.getSuccess(result);
                }
            }
        });
    }

    @Override
    public void getQQGroupNo() {
        Observable observable = ScheduleMethods.getInstance().getQQGroupNo();
        toSubscribe(observable,  new Subscriber<QQBean>() {
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
            public void onNext(QQBean result) {
                if (mMvpView!=null && result!=null){
                    mMvpView.getQQSuccess(result.getQqGroupNo());
                }
            }
        });
    }

    @Override
    public void getAppVersion() {
        Observable observable = ScheduleMethods.getInstance().getAppVersion();
        toSubscribe(observable,  new Subscriber<VersionBean>() {
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
            public void onNext(VersionBean result) {
                if (mMvpView!=null && result!=null){
                    mMvpView.getVersionSuccess(result);
                }
            }
        });
    }


}
