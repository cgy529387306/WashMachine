package com.android.mb.wash.presenter;

import android.text.TextUtils;

import com.android.mb.wash.base.BaseMvpPresenter;
import com.android.mb.wash.entity.PostListData;
import com.android.mb.wash.presenter.interfaces.IPostListPresenter;
import com.android.mb.wash.service.ScheduleMethods;
import com.android.mb.wash.view.interfaces.IPostListView;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class PostListPresenter extends BaseMvpPresenter<IPostListView> implements IPostListPresenter {


    @Override
    public void getPostList(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().getDynamicList(requestMap);
        toSubscribe(observable,  new Subscriber<PostListData>() {
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
            public void onNext(PostListData result) {
                if (mMvpView!=null){
                    mMvpView.getSuccess(result);
                    mMvpView.dismissProgressDialog();
                }
            }
        });
    }
}
