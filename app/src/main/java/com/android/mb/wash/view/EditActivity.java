package com.android.mb.wash.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.android.mb.wash.R;
import com.android.mb.wash.base.BaseActivity;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.ToastHelper;

public class EditActivity extends BaseActivity {

    private String mTitle;
    private String mValue;
    private String mHint;
    private String mEmptyHint;
    private EditText mEditText;

    @Override
    protected void loadIntent() {
        mTitle = getIntent().getStringExtra("title");
        mValue = getIntent().getStringExtra("value");
        mHint = getIntent().getStringExtra("hint");
        mEmptyHint = getIntent().getStringExtra("emptyHint");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit;
    }

    @Override
    protected void initTitle() {
        setTitleText(mTitle);
        setRightText("保存");
    }

    @Override
    protected void onRightAction() {
        super.onRightAction();
        String editText = mEditText.getText().toString().trim();
        if (Helper.isEmpty(editText)){
            ToastHelper.showLongToast(mEmptyHint);
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("editText",editText);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void bindViews() {
        mEditText = findViewById(R.id.editView);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mEditText.setText(mValue==null?"":mValue);
        mEditText.setSelection(mValue==null?0:mValue.length());
        mEditText.setHint(mHint==null?"":mHint);
    }

    @Override
    protected void setListener() {

    }
}
