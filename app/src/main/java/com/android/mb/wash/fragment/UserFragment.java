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
import com.android.mb.wash.utils.ImageUtils;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.utils.PreferencesHelper;
import com.android.mb.wash.utils.ProjectHelper;
import com.android.mb.wash.view.FeedbackActivity;
import com.android.mb.wash.view.HistoryActivity;
import com.android.mb.wash.view.InviteActivity;
import com.android.mb.wash.view.LikeActivity;
import com.android.mb.wash.view.LoginActivity;
import com.android.mb.wash.view.SettingActivity;
import com.android.mb.wash.view.interfaces.IExtraView;
import com.android.mb.wash.widget.CircleImageView;

import java.util.Objects;

import rx.functions.Action1;

/**
 * Created by cgy on 19/1/19.
 */

public class UserFragment extends BaseMvpFragment<ExtraPresenter,IExtraView> implements IExtraView,View.OnClickListener{

    private TextView mTvLogin;
    private TextView mTvName;
    private CircleImageView mIvAvatar;
    private TextView mTvCountHistory,mTvCountCache,mTvCountFavor,mTvCountPlay;
    private TextView mTvVip;
    private String mQQGroup;

    @Override
    protected int getLayoutId() {
        return R.layout.frg_user;
    }

    @Override
    protected void bindViews(View view) {
        mIvAvatar = view.findViewById(R.id.iv_avatar);
        mTvLogin = view.findViewById(R.id.tv_login);
        mTvName = view.findViewById(R.id.tv_userName);
        mTvCountHistory = view.findViewById(R.id.tv_count_history);
        mTvCountCache = view.findViewById(R.id.tv_count_cache);
        mTvCountFavor = view.findViewById(R.id.tv_count_favor);
        mTvCountPlay = view.findViewById(R.id.tv_count_play);
        mTvVip = view.findViewById(R.id.tv_vip);
    }

    @Override
    protected void processLogic() {
        initUserInfo();
        mPresenter.getCountData();
        mPresenter.getQQGroupNo();
        mPresenter.getAppVersion();
        regiestEvent(ProjectConstants.EVENT_UPDATE_USER_INFO, new Action1<Events<?>>() {
            @Override
            public void call(Events<?> events) {
                initUserInfo();
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
        mTvVip.setOnClickListener(this);
        mTvLogin.setOnClickListener(this);
        mRootView.findViewById(R.id.tv_promote).setOnClickListener(this);
        mRootView.findViewById(R.id.tv_feedback).setOnClickListener(this);
        mRootView.findViewById(R.id.tv_potato).setOnClickListener(this);
        mRootView.findViewById(R.id.rl_history).setOnClickListener(this);
        mRootView.findViewById(R.id.rl_cache).setOnClickListener(this);
        mRootView.findViewById(R.id.rl_favor).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_setting).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_invite).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_promote){
            NavigationHelper.startActivity(getActivity(), InviteActivity.class,null,false);
        }else if (id == R.id.tv_feedback){
            NavigationHelper.startActivity(getActivity(), FeedbackActivity.class,null,false);
        }else if (id == R.id.tv_potato){
            if (Helper.isNotEmpty(mQQGroup)){
                ProjectHelper.openUrlWithIntent(getActivity(),mQQGroup);
            }
        }else if (id == R.id.rl_history){
            if (CurrentUser.getInstance().isLogin()){
                NavigationHelper.startActivity(getActivity(), HistoryActivity.class,null,false);
            }else{
                NavigationHelper.startActivity(getActivity(), LoginActivity.class,null,false);
            }
        }else if (id == R.id.rl_cache){
            if (CurrentUser.getInstance().isLogin()){

            }else{
                NavigationHelper.startActivity(getActivity(), LoginActivity.class,null,false);
            }
        }else if (id == R.id.rl_favor){
            if (CurrentUser.getInstance().isLogin()){
                NavigationHelper.startActivity(getActivity(), LikeActivity.class,null,false);
            }else{
                NavigationHelper.startActivity(getActivity(), LoginActivity.class,null,false);
            }
        }else if (id == R.id.btn_setting){
            //TODO
            NavigationHelper.startActivity(getActivity(), SettingActivity.class,null,false);
        }else if (id == R.id.tv_login){
            if (!CurrentUser.getInstance().isLogin()){
                NavigationHelper.startActivity(getActivity(), LoginActivity.class,null,false);
            }
        }else if (id == R.id.btn_invite){
            NavigationHelper.startActivity(getActivity(), InviteActivity.class,null,false);
        }else if (id == R.id.tv_vip){
            NavigationHelper.startActivity(getActivity(), InviteActivity.class,null,false);
        }
    }

    private void initUserInfo(){
        if (CurrentUser.getInstance()!=null && CurrentUser.getInstance().isLogin()){
            mTvLogin.setText("Lv0小白");
            mTvName.setText(CurrentUser.getInstance().getNickname());
            ImageUtils.displayAvatar(mIvAvatar,CurrentUser.getInstance().getAvatar_url());
        }else{
            mTvName.setText("看官大人请登录");
            mIvAvatar.setImageResource(R.mipmap.ic_head_s);
        }
    }

    @Override
    protected ExtraPresenter createPresenter() {
        return new ExtraPresenter();
    }

    @Override
    public void getSuccess(CountData result) {
        if (result!=null){
            PreferencesHelper.getInstance().putInt(ProjectConstants.KEY_REMAIN_COUNT,result.getRemainCount());
            mTvCountHistory.setText(String.format(getString(R.string.count_history_pre),result.getHistoryCount()));
            mTvCountFavor.setText(String.format(getString(R.string.count_favor_pre),result.getLikeCount()));
            mTvCountPlay.setText(String.format(getString(R.string.count_play_pre),result.getWatchCount()-result.getRemainCount(),result.getWatchCount()));
        }
    }

    @Override
    public void getQQSuccess(String result) {
        mQQGroup = result;
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
