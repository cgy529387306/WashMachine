package com.android.mb.wash.presenter;

import android.text.TextUtils;

import com.android.mb.wash.base.BaseMvpPresenter;
import com.android.mb.wash.entity.VideoListData;
import com.android.mb.wash.presenter.interfaces.ILikePresenter;
import com.android.mb.wash.service.ScheduleMethods;
import com.android.mb.wash.view.interfaces.ILikeView;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class LikePresenter extends BaseMvpPresenter<ILikeView> implements ILikePresenter {

    @Override
    public void getLike(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().getLike(requestMap);
        toSubscribe(observable,  new Subscriber<VideoListData>() {
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
            public void onNext(VideoListData result) {
                if (mMvpView!=null){
                    mMvpView.getSuccess(result);
                    mMvpView.dismissProgressDialog();
                }
            }
        });
    }

    @Override
    public void delLike(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().delLike(requestMap);
        toSubscribe(observable,  new Subscriber<Object>() {
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
            public void onNext(Object result) {
                if (mMvpView!=null){
                    mMvpView.deleteSuccess(result);
                }
            }
        });
    }
}
