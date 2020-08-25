package com.android.mb.wash.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.mb.wash.R;
import com.android.mb.wash.base.BaseActivity;
import com.android.mb.wash.entity.CurrentUser;
import com.android.mb.wash.utils.AppHelper;
import com.android.mb.wash.utils.CacheHelper;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.utils.ProgressDialogHelper;
import com.android.mb.wash.utils.ToastHelper;

/**
 * 登录
 * Created by cgy on 2018\8\20 0020.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private TextView mTvCache;
    private TextView mTvVersion;
    private TextView mTvDate;

    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mTvCache.setText("0kB");
                    ProgressDialogHelper.dismissProgressDialog();
                    ToastHelper.showToast("清理完成");
                    break;
            }
            super.handleMessage(msg);
        }

    };
    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initTitle() {
        setTitleText("设置");
    }

    @Override
    protected void bindViews() {
        mTvCache = findViewById(R.id.tv_cache);
        mTvVersion = findViewById(R.id.tv_version);
        mTvDate = findViewById(R.id.tv_date);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        initData();
    }

    @Override
    protected void setListener() {
        findViewById(R.id.btn_account).setOnClickListener(this);
        findViewById(R.id.btn_agreement).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
    }

    private void initData(){
        mTvCache.setText(CacheHelper.getCacheSize(this));
        mTvVersion.setText("当前版本V"+AppHelper.getCurrentVersionName());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_account){
            if (CurrentUser.getInstance().isLogin()){
                NavigationHelper.startActivity(this, AccountActivity.class,null,false);
            }else{
                NavigationHelper.startActivity(this, LoginActivity.class,null,false);
            }

        }else if (id == R.id.btn_agreement){

        }else if (id == R.id.btn_clear){
            new MaterialDialog.Builder(mContext).title("清理缓存").content("是否清理当前app缓存?")
                    .positiveText("确定").negativeText("取消").onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    CacheHelper.cleanCache(mContext);
                    Message message = new Message();
                    message.what = 1;
                    mHandler.sendMessage(message);
                }
            }).show();
        }
    }



}
