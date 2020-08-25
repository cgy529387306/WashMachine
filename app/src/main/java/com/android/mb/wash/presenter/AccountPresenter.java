package com.android.mb.wash.presenter;

import android.text.TextUtils;

import com.android.mb.wash.base.BaseMvpPresenter;
import com.android.mb.wash.entity.Avatar;
import com.android.mb.wash.entity.UserBean;
import com.android.mb.wash.presenter.interfaces.IAccountPresenter;
import com.android.mb.wash.service.ScheduleMethods;
import com.android.mb.wash.view.interfaces.IAccountView;

import java.io.File;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class AccountPresenter extends BaseMvpPresenter<IAccountView> implements IAccountPresenter {


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

    @Override
    public void updateInfo(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().updateInfo(requestMap);
        toSubscribe(observable,  new Subscriber<UserBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(mMvpView!=null){
                    mMvpView.dismissProgressDialog();
                    if (!TextUtils.isEmpty(e.getMessage())){
                        mMvpView.showToastMessage(e.getMessage());
                    }
                }
            }

            @Override
            public void onNext(UserBean result) {
                if (mMvpView!=null){
                    mMvpView.dismissProgressDialog();
                    mMvpView.updateInfo(result);
                }
            }
        });
    }

    @Override
    public void uploadAvatar(File file) {
        Observable observable = ScheduleMethods.getInstance().uploadAvatar(file);
        toSubscribe(observable,  new Subscriber<Avatar>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(mMvpView!=null){
                    mMvpView.dismissProgressDialog();
                    if (!TextUtils.isEmpty(e.getMessage())){
                        mMvpView.showToastMessage(e.getMessage());
                    }
                }
            }

            @Override
            public void onNext(Avatar result) {
                if (mMvpView!=null){
                    mMvpView.dismissProgressDialog();
                    mMvpView.uploadAvatar(result);
                }
            }
        });
    }

}
