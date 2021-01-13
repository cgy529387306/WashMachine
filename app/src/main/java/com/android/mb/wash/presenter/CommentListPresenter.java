package com.android.mb.wash.presenter;

import android.text.TextUtils;

import com.android.mb.wash.base.BaseMvpPresenter;
import com.android.mb.wash.entity.CommentListData;
import com.android.mb.wash.entity.PostListData;
import com.android.mb.wash.presenter.interfaces.ICommentListPresenter;
import com.android.mb.wash.service.ScheduleMethods;
import com.android.mb.wash.view.interfaces.ICommentListView;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class CommentListPresenter extends BaseMvpPresenter<ICommentListView> implements ICommentListPresenter {


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
                    mMvpView.getSuccess(result);
                }
            }
        });
    }

    @Override
    public void deleteComment(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().deleteComment(requestMap);
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
                    mMvpView.deleteSuccess(result);
                }
            }
        });
    }
}
