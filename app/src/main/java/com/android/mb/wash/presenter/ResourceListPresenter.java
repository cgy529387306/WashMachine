package com.android.mb.wash.presenter;

import android.text.TextUtils;

import com.android.mb.wash.base.BaseMvpPresenter;
import com.android.mb.wash.entity.PostListData;
import com.android.mb.wash.entity.ResourceListData;
import com.android.mb.wash.presenter.interfaces.IPostListPresenter;
import com.android.mb.wash.presenter.interfaces.IResourceListPresenter;
import com.android.mb.wash.service.ScheduleMethods;
import com.android.mb.wash.view.interfaces.IPostListView;
import com.android.mb.wash.view.interfaces.IResourceListView;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class ResourceListPresenter extends BaseMvpPresenter<IResourceListView> implements IResourceListPresenter {


    @Override
    public void getList(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().getResourceList(requestMap);
        toSubscribe(observable,  new Subscriber<ResourceListData>() {
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
            public void onNext(ResourceListData result) {
                if (mMvpView!=null){
                    mMvpView.getSuccess(result);
                    mMvpView.dismissProgressDialog();
                }
            }
        });
    }
}
