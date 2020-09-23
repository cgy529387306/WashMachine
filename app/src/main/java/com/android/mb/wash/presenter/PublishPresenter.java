package com.android.mb.wash.presenter;

import android.text.TextUtils;

import com.android.mb.wash.base.BaseMvpPresenter;
import com.android.mb.wash.entity.SpecialData;
import com.android.mb.wash.presenter.interfaces.IPublishPresenter;
import com.android.mb.wash.presenter.interfaces.ISpecialPresenter;
import com.android.mb.wash.service.ScheduleMethods;
import com.android.mb.wash.view.interfaces.IPublishView;
import com.android.mb.wash.view.interfaces.ISpecialView;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class PublishPresenter extends BaseMvpPresenter<IPublishView> implements IPublishPresenter {

    @Override
    public void publishDynamic(Map<String,Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().publishDynamic(requestMap);
        toSubscribe(observable,  new Subscriber<Object>() {
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
            public void onNext(Object result) {
                if (mMvpView!=null){
                    mMvpView.publishSuccess(result);
                }
            }
        });
    }
}
