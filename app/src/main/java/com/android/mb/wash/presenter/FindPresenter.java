package com.android.mb.wash.presenter;

import android.text.TextUtils;

import com.android.mb.wash.base.BaseMvpPresenter;
import com.android.mb.wash.entity.VideoListData;
import com.android.mb.wash.presenter.interfaces.IFindPresenter;
import com.android.mb.wash.service.ScheduleMethods;
import com.android.mb.wash.view.interfaces.IFindView;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class FindPresenter extends BaseMvpPresenter<IFindView> implements IFindPresenter {


    @Override
    public void getFindData(Map<String,Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().getFindData(requestMap);
        toSubscribe(observable,  new Subscriber<VideoListData>() {
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
            public void onNext(VideoListData result) {
                if (mMvpView!=null){
                    mMvpView.getSuccess(result);
                }
            }
        });
    }

    @Override
    public void praise(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().praise(requestMap);
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
                    mMvpView.praise(result);
                }
            }
        });
    }

    @Override
    public void watch(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().watch(requestMap);
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
                    mMvpView.watch(result);
                }
            }
        });
    }

}
