package com.android.mb.wash.utils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class HttpManager {

    private static volatile HttpManager mInstance;
    private CompositeSubscription mCompositeSubscription;

    private HttpManager(){
        mCompositeSubscription = new CompositeSubscription();
    }

    public static HttpManager instance() {
        if(mInstance == null) {
            synchronized (HttpManager.class) {
                if(mInstance == null) {
                    mInstance = new HttpManager();
                }
            }
        }
        return mInstance;
    }

    public <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s));
    }

    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
            //解绑完不置null第二次绑定会有问题。
            mCompositeSubscription = null;
        }
    }


}
