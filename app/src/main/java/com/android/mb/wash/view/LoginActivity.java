package com.android.mb.wash.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.mb.wash.R;
import com.android.mb.wash.base.BaseMvpActivity;
import com.android.mb.wash.constants.ProjectConstants;
import com.android.mb.wash.entity.CurrentUser;
import com.android.mb.wash.entity.UserBean;
import com.android.mb.wash.presenter.LoginPresenter;
import com.android.mb.wash.utils.ActivityManager;
import com.android.mb.wash.utils.AppHelper;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.utils.ToastHelper;
import com.android.mb.wash.view.interfaces.ILoginView;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 * Created by cgy on 2018\8\20 0020.
 */

public class LoginActivity extends BaseMvpActivity<LoginPresenter,ILoginView> implements ILoginView, View.OnClickListener{

    private TextView mTvLogin;
    private EditText mEtAccount;
    private EditText mEtPwd;
    private boolean mIsReLogin;

    @Override
    protected void loadIntent() {
        mIsReLogin = getIntent().getBooleanExtra("isReLogin", false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initTitle() {
        hideActionbar();
    }

    @Override
    protected void bindViews() {
        mTvLogin = findViewById(R.id.tv_login);
        mEtAccount = findViewById(R.id.et_account);
        mEtPwd = findViewById(R.id.et_pwd);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
    }

    @Override
    protected void setListener() {
        findViewById(R.id.tv_forget_pwd).setOnClickListener(this);
        findViewById(R.id.tv_secret).setOnClickListener(this);
        findViewById(R.id.tv_agreement).setOnClickListener(this);
        mTvLogin.setOnClickListener(this);
        mEtAccount.addTextChangedListener(myTextWatcher);
        mEtPwd.addTextChangedListener(myTextWatcher);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_login){
            doLogin();
        }else if (id == R.id.tv_forget_pwd){
            NavigationHelper.startActivity(mContext,ForgetPwdActivity.class,null,false);
        }else if (id == R.id.tv_secret){
            NavigationHelper.startActivity(mContext,ForgetPwdActivity.class,null,false);
        }else if (id == R.id.tv_agreement){
            NavigationHelper.startActivity(mContext,ForgetPwdActivity.class,null,false);
        }
    }

    private TextWatcher myTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String account = mEtAccount.getText().toString().trim();
            String password = mEtPwd.getText().toString().trim();
            if (Helper.isNotEmpty(account) && Helper.isNotEmpty(password)){
                mTvLogin.setEnabled(true);
                mTvLogin.setBackgroundResource(R.drawable.shape_btn_login_s);
            }else{
                mTvLogin.setEnabled(false);
                mTvLogin.setBackgroundResource(R.drawable.shape_btn_login_n);
            }
        }
    };

    private void doLogin(){
        String account = mEtAccount.getText().toString().trim();
        String pwd = mEtPwd.getText().toString().trim();
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("account",account);
        requestMap.put("password",pwd);
        mPresenter.userLogin(requestMap);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void loginSuccess(UserBean result) {
        if (result!=null ){
            AppHelper.hideSoftInputFromWindow(mEtAccount);
            CurrentUser.getInstance().login(result);
            showToastMessage("登录成功");
            sendMsg(ProjectConstants.EVENT_UPDATE_USER_INFO,null);
            NavigationHelper.startActivity(LoginActivity.this, MainActivity.class, null, true);
        }
    }

    private static final long DOUBLE_CLICK_INTERVAL = 2000;
    private long mLastClickTimeMills = 0;
    @Override
    public void onBackPressed() {
        if (mIsReLogin){
            if (System.currentTimeMillis() - mLastClickTimeMills > DOUBLE_CLICK_INTERVAL) {
                ToastHelper.showToast("再按一次返回退出");
                mLastClickTimeMills = System.currentTimeMillis();
                return;
            }
            ActivityManager.getInstance().closeAllActivity();
        }
        finish();
    }


}
