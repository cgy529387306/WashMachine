package com.android.mb.wash.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.android.mb.wash.R;
import com.android.mb.wash.base.BaseActivity;
import com.android.mb.wash.fragment.ResourceFragment;

import java.util.ArrayList;
import java.util.List;

public class CompanyDescActivity extends BaseActivity implements View.OnClickListener {

    private String[] mTabTitles = {"工程案例", "品牌骄傲"};
    private List<Fragment> mTabFragments = new ArrayList<>();

    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_company_desc;
    }

    @Override
    protected void initTitle() {
        hideActionbar();
    }

    @Override
    protected void bindViews() {
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        for (int i = 0; i < mTabTitles.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(mTabTitles[i]));
            mTabFragments.add(ResourceFragment.newInstance(i+1));
        }
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return mTabFragments.get(position);
            }

            @Override
            public int getCount() {
                return mTabFragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTabTitles[position];
            }
        });
        tabLayout.setupWithViewPager(viewPager,false);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_close).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_close){
            finish();
        }
    }

}
