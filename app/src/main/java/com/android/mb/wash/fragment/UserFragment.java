package com.android.mb.wash.fragment;

import android.view.View;
import android.widget.TextView;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.android.mb.wash.R;
import com.android.mb.wash.base.BaseMvpFragment;
import com.android.mb.wash.constants.ProjectConstants;
import com.android.mb.wash.entity.CountData;
import com.android.mb.wash.entity.CurrentUser;
import com.android.mb.wash.entity.VersionBean;
import com.android.mb.wash.presenter.ExtraPresenter;
import com.android.mb.wash.rxbus.Events;
import com.android.mb.wash.utils.AppHelper;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.view.ChangePwdActivity;
import com.android.mb.wash.view.LoginActivity;
import com.android.mb.wash.view.PersonInfoActivity;
import com.android.mb.wash.view.interfaces.IExtraView;
import com.android.mb.wash.widget.CircleImageView;

import java.util.Objects;

import rx.functions.Action1;

/**
 * Created by cgy on 19/1/19.
 */

public class UserFragment extends BaseMvpFragment<ExtraPresenter,IExtraView> implements IExtraView,View.OnClickListener{

    private TextView mTvName;
    private CircleImageView mIvAvatar;
    private TextView mTvPost,mTvFavor,mTvComment;

    @Override
    protected int getLayoutId() {
        return R.layout.frg_user;
    }

    @Override
    protected void bindViews(View view) {
        mIvAvatar = view.findViewById(R.id.iv_avatar);
        mTvName = view.findViewById(R.id.tv_name);
        mTvPost = view.findViewById(R.id.tv_post_count);
        mTvFavor = view.findViewById(R.id.tv_favor_count);
        mTvComment = view.findViewById(R.id.tv_comment_count);
    }

    @Override
    protected void processLogic() {
        mPresenter.getCountData();
        mPresenter.getQQGroupNo();
        mPresenter.getAppVersion();
        regiestEvent(ProjectConstants.EVENT_UPDATE_USER_INFO, new Action1<Events<?>>() {
            @Override
            public void call(Events<?> events) {
                mPresenter.getCountData();
            }
        });
        regiestEvent(ProjectConstants.EVENT_GET_EXTRA_DATA, new Action1<Events<?>>() {
            @Override
            public void call(Events<?> events) {
                mPresenter.getCountData();
            }
        });
    }

    @Override
    protected void setListener() {
        mRootView.findViewById(R.id.rl_person_info).setOnClickListener(this);
        mRootView.findViewById(R.id.rl_post).setOnClickListener(this);
        mRootView.findViewById(R.id.rl_favor).setOnClickListener(this);
        mRootView.findViewById(R.id.rl_comment).setOnClickListener(this);
        mRootView.findViewById(R.id.rl_update_pwd).setOnClickListener(this);
        mRootView.findViewById(R.id.rl_favor).setOnClickListener(this);
        mRootView.findViewById(R.id.tv_logout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.rl_person_info){
            NavigationHelper.startActivity(getActivity(), PersonInfoActivity.class,null,false);
        }else if (id == R.id.rl_post){
        }else if (id == R.id.rl_favor){
        }else if (id == R.id.rl_comment){

        }else if (id == R.id.rl_update_pwd){
            if (CurrentUser.getInstance().isLogin()){
                NavigationHelper.startActivity(getActivity(), ChangePwdActivity.class,null,false);
            }else{
                NavigationHelper.startActivity(getActivity(), LoginActivity.class,null,false);
            }
        }else if (id == R.id.tv_logout){

        }
    }


    @Override
    protected ExtraPresenter createPresenter() {
        return new ExtraPresenter();
    }

    @Override
    public void getSuccess(CountData result) {
        if (result!=null){
        }
    }

    @Override
    public void getQQSuccess(String result) {
    }

    @Override
    public void getVersionSuccess(VersionBean result) {
        if (Helper.isNotEmpty(result)){
            try {
                if (Integer.parseInt(result.getAndroidVersion()) > AppHelper.getCurrentVersion()){
                    AllenVersionChecker
                            .getInstance()
                            .downloadOnly(
                                    UIData.create().setDownloadUrl(result.getAndroidDownloadUrl()).setTitle("检测到新版本,是否更新版本？").setContent(result.getAndroidUpdContent())
                            )
                            .setForceRedownload(result.getAndroidIsForce()==1).setShowNotification(true).executeMission(Objects.requireNonNull(getActivity()));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
