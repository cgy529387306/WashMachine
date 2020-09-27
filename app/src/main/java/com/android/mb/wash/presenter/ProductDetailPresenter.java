package com.android.mb.wash.presenter;

import android.text.TextUtils;

import com.android.mb.wash.base.BaseMvpPresenter;
import com.android.mb.wash.entity.VideoData;
import com.android.mb.wash.presenter.interfaces.IProductDetailPresenter;
import com.android.mb.wash.service.ScheduleMethods;
import com.android.mb.wash.view.interfaces.IPostDetailView;
import com.android.mb.wash.view.interfaces.IProductDetailView;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class ProductDetailPresenter extends BaseMvpPresenter<IProductDetailView> implements IProductDetailPresenter {


    @Override
    public void getDetail(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().getProductDetail(requestMap);
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
                    mMvpView.getDetail(result);
                }
            }
        });
    }
}
