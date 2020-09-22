package com.android.mb.wash.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mb.wash.R;
import com.android.mb.wash.base.BaseMvpActivity;
import com.android.mb.wash.presenter.ChangePwdPresenter;
import com.android.mb.wash.utils.AppHelper;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.ToastHelper;
import com.android.mb.wash.view.interfaces.IChangePwdView;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改密码
 * Created by cgy on 2018\8\20 0020.
 */

public class ChangePwdActivity extends BaseMvpActivity<ChangePwdPresenter,IChangePwdView> implements IChangePwdView, View.OnClickListener{

    private TextView mTvRegister;
    private EditText mEtOldPwd;
    private EditText mEtNewPwd;
    private EditText mEtNewPwd2;
    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void initTitle() {
       setTitleText("修改密码");
    }

    @Override
    protected void bindViews() {
        mTvRegister = findViewById(R.id.tv_register);
        mEtOldPwd = findViewById(R.id.et_old_pwd);
        mEtNewPwd = findViewById(R.id.et_new_pwd);
        mEtNewPwd2 = findViewById(R.id.et_new_pwd_confirm);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {
        mTvRegister.setOnClickListener(this);
        mEtOldPwd.addTextChangedListener(myTextWatcher);
        mEtNewPwd.addTextChangedListener(myTextWatcher);
        mEtNewPwd2.addTextChangedListener(myTextWatcher);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_register){
            doRegister();
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
            String oldPwd = mEtOldPwd.getText().toString().trim();
            String newPwd = mEtNewPwd.getText().toString().trim();
            String newPwd2 = mEtNewPwd2.getText().toString().trim();
            if (Helper.isNotEmpty(oldPwd) && Helper.isNotEmpty(newPwd) && Helper.isNotEmpty(newPwd2)){
                mTvRegister.setEnabled(true);
                mTvRegister.setBackgroundResource(R.drawable.shape_btn_login_s);
            }else{
                mTvRegister.setEnabled(false);
                mTvRegister.setBackgroundResource(R.drawable.shape_btn_login_n);
            }
        }
    };



    private void doRegister(){
        String oldPwd = mEtOldPwd.getText().toString().trim();
        String newPwd = mEtNewPwd.getText().toString().trim();
        String newPwd2 = mEtNewPwd2.getText().toString().trim();
        if (!newPwd.equals(newPwd2)) {
            ToastHelper.showLongToast("两次输入密码不一致");
            return;
        }
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("oldPassword",oldPwd);
        requestMap.put("newPassword",newPwd);
        mPresenter.updatePassword(requestMap);
    }

    @Override
    protected ChangePwdPresenter createPresenter() {
        return new ChangePwdPresenter();
    }

    @Override
    public void changeSuccess(Object result) {
        AppHelper.hideSoftInputFromWindow(mEtOldPwd);
        showToastMessage("修改成功");
        finish();
    }

}
