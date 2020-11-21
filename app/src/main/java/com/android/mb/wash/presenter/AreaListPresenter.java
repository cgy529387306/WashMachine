package com.android.mb.wash.presenter;

import android.text.TextUtils;

import com.android.mb.wash.base.BaseMvpPresenter;
import com.android.mb.wash.entity.AreaBean;
import com.android.mb.wash.entity.AreaListData;
import com.android.mb.wash.presenter.interfaces.IAreaListPresenter;
import com.android.mb.wash.service.ScheduleMethods;
import com.android.mb.wash.view.interfaces.IAreaListView;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class AreaListPresenter extends BaseMvpPresenter<IAreaListView> implements IAreaListPresenter {


    @Override
    public void getList(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().getAreaList(requestMap);
        toSubscribe(observable,  new Subscriber<List<AreaBean>>() {
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
            public void onNext(List<AreaBean> result) {
                if (mMvpView!=null){
                    mMvpView.getSuccess(result);
                }
            }
        });
    }
}
