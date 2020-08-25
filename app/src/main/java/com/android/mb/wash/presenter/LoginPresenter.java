package com.android.mb.wash.presenter;

import android.text.TextUtils;

import com.android.mb.wash.base.BaseMvpPresenter;
import com.android.mb.wash.entity.UserBean;
import com.android.mb.wash.presenter.interfaces.ILoginPresenter;
import com.android.mb.wash.service.ScheduleMethods;
import com.android.mb.wash.view.interfaces.ILoginView;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class LoginPresenter extends BaseMvpPresenter<ILoginView> implements ILoginPresenter {


    @Override
    public void userLogin(Map<String,Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().userLogin(requestMap);
        toSubscribe(observable,  new Subscriber<UserBean>() {
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
            public void onNext(UserBean result) {
                if (mMvpView!=null){
                    mMvpView.loginSuccess(result);
                }
            }
        });
    }

}
