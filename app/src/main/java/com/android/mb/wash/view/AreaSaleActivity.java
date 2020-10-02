package com.android.mb.wash.view;

import android.os.Bundle;
import android.view.View;

import com.android.mb.wash.R;
import com.android.mb.wash.base.BaseActivity;
import com.android.mb.wash.utils.NavigationHelper;

public class AreaSaleActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_area_sale;
    }

    @Override
    protected void initTitle() {
        setTitleText("专卖店/专区");
    }

    @Override
    protected void bindViews() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {
        findViewById(R.id.rl_sale).setOnClickListener(this);
        findViewById(R.id.rl_area).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Bundle bundle = new Bundle();
        if (id == R.id.rl_sale){
            bundle.putInt("type",4);
            NavigationHelper.startActivity(mContext, ResourceActivity.class,bundle,false);
        } else if (id == R.id.rl_area){
            bundle.putInt("type",5);
            NavigationHelper.startActivity(mContext, ResourceActivity.class,bundle,false);
        }
    }
}
