package com.android.mb.wash.presenter;

import android.text.TextUtils;

import com.android.mb.wash.base.BaseMvpPresenter;
import com.android.mb.wash.entity.CommentListData;
import com.android.mb.wash.entity.VideoData;
import com.android.mb.wash.presenter.interfaces.IPostDetailPresenter;
import com.android.mb.wash.service.ScheduleMethods;
import com.android.mb.wash.view.interfaces.IPostDetailView;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class PostDetailPresenter extends BaseMvpPresenter<IPostDetailView> implements IPostDetailPresenter {


    @Override
    public void getPostDetail(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().getVideoDetail(requestMap);
        toSubscribe(observable,  new Subscriber<VideoData>() {
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
            public void onNext(VideoData result) {
                if (mMvpView!=null){
                    mMvpView.getPostDetail(result);
                }
            }
        });
    }

    @Override
    public void comment(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().comment(requestMap);
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
                    mMvpView.comment(result);
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
    public void getPostComments(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().getCommentList(requestMap);
        toSubscribe(observable,  new Subscriber<CommentListData>() {
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
            public void onNext(CommentListData result) {
                if (mMvpView!=null){
                    mMvpView.getPostComments(result);
                }
            }
        });
    }
}
