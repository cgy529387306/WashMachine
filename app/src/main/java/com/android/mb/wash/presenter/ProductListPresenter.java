package com.android.mb.wash.presenter;

import android.text.TextUtils;

import com.android.mb.wash.base.BaseMvpPresenter;
import com.android.mb.wash.entity.Advert;
import com.android.mb.wash.entity.Category;
import com.android.mb.wash.entity.ProductListData;
import com.android.mb.wash.presenter.interfaces.IProductListPresenter;
import com.android.mb.wash.service.ScheduleMethods;
import com.android.mb.wash.view.interfaces.IProductListView;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class ProductListPresenter extends BaseMvpPresenter<IProductListView> implements IProductListPresenter {


    @Override
    public void getCategoryList(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().getCategoryList(requestMap);
        toSubscribe(observable,  new Subscriber<List<Category>>() {
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
            public void onNext(List<Category> result) {
                if (mMvpView!=null){
                    mMvpView.getSuccess(result);
                    mMvpView.dismissProgressDialog();
                }
            }
        });
    }

    @Override
    public void getProductList(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().getProductList(requestMap);
        toSubscribe(observable,  new Subscriber<ProductListData>() {
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
            public void onNext(ProductListData result) {
                if (mMvpView!=null){
                    mMvpView.getProductSuccess(result);
                    mMvpView.dismissProgressDialog();
                }
            }
        });
    }

    @Override
    public void getCateAdverList(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().getCateAdverList(requestMap);
        toSubscribe(observable,  new Subscriber<List<Advert>>() {
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
            public void onNext(List<Advert> result) {
                if (mMvpView!=null){
                    mMvpView.getAdSuccess(result);
                }
            }
        });
    }

}
