package com.android.mb.wash.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.mb.wash.R;
import com.android.mb.wash.base.BaseMvpFragment;
import com.android.mb.wash.constants.ProjectConstants;
import com.android.mb.wash.entity.Avatar;
import com.android.mb.wash.entity.CurrentUser;
import com.android.mb.wash.entity.UserBean;
import com.android.mb.wash.presenter.AccountPresenter;
import com.android.mb.wash.rxbus.Events;
import com.android.mb.wash.utils.ImageUtils;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.view.ChangePwdActivity;
import com.android.mb.wash.view.LoginActivity;
import com.android.mb.wash.view.MyCommentListActivity;
import com.android.mb.wash.view.MyPostListActivity;
import com.android.mb.wash.view.PersonInfoActivity;
import com.android.mb.wash.view.interfaces.IAccountView;
import com.android.mb.wash.widget.CircleImageView;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

/**
 * Created by cgy on 19/1/19.
 */

public class UserFragment extends BaseMvpFragment<AccountPresenter, IAccountView> implements IAccountView,View.OnClickListener{

    private TextView mTvName;
    private CircleImageView mIvAvatar;
    private TextView mTvPost,mTvFavor,mTvComment;
    private TextView mTvLogout;

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
        mTvLogout = view.findViewById(R.id.tv_logout);
    }

    @Override
    protected void processLogic() {
        initUserInfo();
        getUserInfo();
        regiestEvent(ProjectConstants.EVENT_UPDATE_USER_INFO, new Action1<Events<?>>() {
            @Override
            public void call(Events<?> events) {
                getUserInfo();
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
        mTvLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.rl_person_info){
            if (CurrentUser.getInstance().isLogin()){
                NavigationHelper.startActivity(getActivity(), PersonInfoActivity.class,null,false);
            }else {
                NavigationHelper.startActivity(getActivity(), LoginActivity.class,null,false);
            }
        }else if (id == R.id.rl_post){
            if (CurrentUser.getInstance().isLogin()){
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                NavigationHelper.startActivity(getActivity(), MyPostListActivity.class,bundle,false);
            }else {
                NavigationHelper.startActivity(getActivity(), LoginActivity.class,null,false);
            }
        }else if (id == R.id.rl_favor){
            if (CurrentUser.getInstance().isLogin()){
                Bundle bundle = new Bundle();
                bundle.putInt("type", 2);
                NavigationHelper.startActivity(getActivity(), MyPostListActivity.class,bundle,false);
            }else {
                NavigationHelper.startActivity(getActivity(), LoginActivity.class,null,false);
            }
        }else if (id == R.id.rl_comment){
            if (CurrentUser.getInstance().isLogin()){
                NavigationHelper.startActivity(getActivity(), MyCommentListActivity.class,null,false);
            }else {
                NavigationHelper.startActivity(getActivity(), LoginActivity.class,null,false);
            }
        }else if (id == R.id.rl_update_pwd){
            if (CurrentUser.getInstance().isLogin()){
                NavigationHelper.startActivity(getActivity(), ChangePwdActivity.class,null,false);
            }else{
                NavigationHelper.startActivity(getActivity(), LoginActivity.class,null,false);
            }
        }else if (id == R.id.tv_logout){
            new MaterialDialog.Builder(mContext).title("提示").content("确定要退出当前账号?")
                    .positiveText("确定").negativeText("取消").onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    CurrentUser.getInstance().loginOut();
                    NavigationHelper.startActivity(getActivity(), LoginActivity.class,null,true);
                }
            }).show();
        }
    }


    @Override
    protected AccountPresenter createPresenter() {
        return new AccountPresenter();
    }

    private void initUserInfo(){
        if (CurrentUser.getInstance().isLogin()){
            mTvName.setText(CurrentUser.getInstance().getNickname());
            ImageUtils.displayAvatar(mIvAvatar,CurrentUser.getInstance().getAvatar_url());
        }
    }

    private void getUserInfo(){
        if (CurrentUser.getInstance().isLogin()){
            mTvLogout.setVisibility(View.VISIBLE);
            Map<String,Object> requestMap = new HashMap<>();
            requestMap.put("userid",CurrentUser.getInstance().getUserid());
            mPresenter.getUserInfo(requestMap);
        }else {
            mTvLogout.setVisibility(View.GONE);
            mTvName.setText("点击登录");
            mIvAvatar.setImageResource(R.mipmap.icon_avatar);
        }
    }

    @Override
    public void getSuccess(UserBean result) {
        if (result!=null){
            CurrentUser.getInstance().login(result);
            initUserInfo();
        }
    }

    @Override
    public void updateInfo(UserBean result) {

    }

    @Override
    public void uploadAvatar(Avatar result) {

    }
}
