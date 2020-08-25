package com.android.mb.wash.presenter;

import android.text.TextUtils;

import com.android.mb.wash.base.BaseMvpPresenter;
import com.android.mb.wash.entity.UserBean;
import com.android.mb.wash.presenter.interfaces.IUserPresenter;
import com.android.mb.wash.service.ScheduleMethods;
import com.android.mb.wash.view.interfaces.IUserView;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class UserPresenter extends BaseMvpPresenter<IUserView> implements IUserPresenter {


    @Override
    public void getUserInfo(Map<String,Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().getUserInfo(requestMap);
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
                    mMvpView.getSuccess(result);
                }
            }
        });
    }

}
