package com.android.mb.wash.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.wash.R;
import com.android.mb.wash.adapter.MyFragmentPagerAdapter;
import com.android.mb.wash.base.BaseActivity;
import com.android.mb.wash.fragment.CompanyFragment;
import com.android.mb.wash.fragment.MainFragment;
import com.android.mb.wash.fragment.ProductFragment;
import com.android.mb.wash.fragment.UserFragment;
import com.android.mb.wash.utils.ToastHelper;
import com.android.mb.wash.widget.FragmentViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private FragmentViewPager mFragmentViewPager;
    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void loadIntent() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initTitle() {
        hideActionbar();
    }

    @Override
    protected void bindViews() {
        mFragmentViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout);
        initTabPager();
    }

    @Override
    protected void onRightAction() {
        super.onRightAction();
//        NavigationHelper.startActivity(mContext,DetailActivity.class,null,false);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mFragmentViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initTabPager(){
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new MainFragment());
        mFragmentList.add(new ProductFragment());
        mFragmentList.add(new CompanyFragment());
        mFragmentList.add(new UserFragment());
        mFragmentViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList));
        mFragmentViewPager.setOffscreenPageLimit(mFragmentList.size());
        mFragmentViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        String[] tabTitles = new String[]{"首页","产品中心","公司","我的"};
        int[] tabImages = new int[]{R.drawable.btn_tab_main,R.drawable.btn_tab_product,R.drawable.btn_tab_company,R.drawable.btn_tab_user};
        for (int i = 0; i < tabTitles.length; i++) {
            TabLayout.Tab tab = mTabLayout.newTab();
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab,null);
            TextView tvTitle = view.findViewById(R.id.tv_tab);
            tvTitle.setText(tabTitles[i]);
            ImageView imgTab =  view.findViewById(R.id.iv_tab);
            imgTab.setImageResource(tabImages[i]);
            tab.setCustomView(view);
            mTabLayout.addTab(tab);
        }
    }

    private static final long DOUBLE_CLICK_INTERVAL = 2000;
    private long mLastClickTimeMills = 0;
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mLastClickTimeMills > DOUBLE_CLICK_INTERVAL) {
            ToastHelper.showToast("再按一次返回退出");
            mLastClickTimeMills = System.currentTimeMillis();
            return;
        }
        finish();
    }

}
