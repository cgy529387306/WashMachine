package com.android.mb.wash.presenter;

import android.text.TextUtils;

import com.android.mb.wash.base.BaseMvpPresenter;
import com.android.mb.wash.presenter.interfaces.IPublishPresenter;
import com.android.mb.wash.service.ScheduleMethods;
import com.android.mb.wash.view.interfaces.IPublishView;

import java.io.File;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class PublishPresenter extends BaseMvpPresenter<IPublishView> implements IPublishPresenter {

    @Override
    public void publishDynamic(List<File> fileList, Map<String,Object> requestMap) {
        mMvpView.showProgressDialog();
        Observable observable = ScheduleMethods.getInstance().publishDynamic(fileList,requestMap);
        toSubscribe(observable,  new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mMvpView.dismissProgressDialog();
                if(mMvpView!=null && !TextUtils.isEmpty(e.getMessage())){
                    mMvpView.showToastMessage(e.getMessage());
                }
            }

            @Override
            public void onNext(Object result) {
                mMvpView.dismissProgressDialog();
                if (mMvpView!=null){
                    mMvpView.publishSuccess(result);
                }
            }
        });
    }
}
