package com.android.mb.wash.presenter;

import android.text.TextUtils;

import com.android.mb.wash.base.BaseMvpPresenter;
import com.android.mb.wash.constants.ProjectConstants;
import com.android.mb.wash.entity.CurrentUser;
import com.android.mb.wash.entity.InviteBean;
import com.android.mb.wash.presenter.interfaces.IInvitePresenter;
import com.android.mb.wash.service.ScheduleMethods;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.JsonHelper;
import com.android.mb.wash.utils.PreferencesHelper;
import com.android.mb.wash.view.interfaces.IInviteView;

import rx.Observable;
import rx.Subscriber;

public class InvitePresenter extends BaseMvpPresenter<IInviteView> implements IInvitePresenter{

    @Override
    public void getPromoCode() {
        String key = ProjectConstants.KEY_INVITE_CODE + CurrentUser.getInstance().getUserid();
        String inviteCode = PreferencesHelper.getInstance().getString(key);
        InviteBean inviteBean = JsonHelper.fromJson(inviteCode,InviteBean.class);
        if (mMvpView!=null && Helper.isNotEmpty(inviteBean)){
            mMvpView.getSuccess(inviteBean);
        }
        Observable observable = ScheduleMethods.getInstance().getPromoCode();
        toSubscribe(observable,  new Subscriber<InviteBean>() {
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
            public void onNext(InviteBean result) {
                if (mMvpView!=null){
                    PreferencesHelper.getInstance().putString(key, JsonHelper.toJson(result));
                    mMvpView.getSuccess(result);
                }
            }
        });
    }


}
