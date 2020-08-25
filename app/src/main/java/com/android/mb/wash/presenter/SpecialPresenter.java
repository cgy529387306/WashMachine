package com.android.mb.wash.presenter;

import android.text.TextUtils;

import com.android.mb.wash.base.BaseMvpPresenter;
import com.android.mb.wash.entity.SpecialData;
import com.android.mb.wash.presenter.interfaces.ISpecialPresenter;
import com.android.mb.wash.service.ScheduleMethods;
import com.android.mb.wash.view.interfaces.ISpecialView;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class SpecialPresenter extends BaseMvpPresenter<ISpecialView> implements ISpecialPresenter {

    @Override
    public void getSpecialData() {
        Observable observable = ScheduleMethods.getInstance().getSpecialData();
        toSubscribe(observable,  new Subscriber<SpecialData>() {
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
            public void onNext(SpecialData result) {
                if (mMvpView!=null){
                    mMvpView.getSpecialData(result);
                }
            }
        });
    }
}
