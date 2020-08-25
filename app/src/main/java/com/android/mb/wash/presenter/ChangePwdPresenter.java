package com.android.mb.wash.presenter;

import android.text.TextUtils;

import com.android.mb.wash.base.BaseMvpPresenter;
import com.android.mb.wash.presenter.interfaces.IChangePwdPresenter;
import com.android.mb.wash.service.ScheduleMethods;
import com.android.mb.wash.view.interfaces.IChangePwdView;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class ChangePwdPresenter extends BaseMvpPresenter<IChangePwdView> implements IChangePwdPresenter {

    @Override
    public void updatePassword(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().updatePassword(requestMap);
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
                    mMvpView.changeSuccess(result);
                }
            }
        });
    }
}
