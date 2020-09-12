package com.android.mb.wash.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.mb.wash.R;
import com.android.mb.wash.adapter.CompanyAdapter;
import com.android.mb.wash.base.BaseMvpFragment;
import com.android.mb.wash.constants.ProjectConstants;
import com.android.mb.wash.entity.SpecialData;
import com.android.mb.wash.presenter.SpecialPresenter;
import com.android.mb.wash.view.interfaces.ISpecialView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by cgy on 16/7/18.
 */
public class CompanyFragment extends BaseMvpFragment<SpecialPresenter,ISpecialView> implements ISpecialView, View.OnClickListener,BaseQuickAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private CompanyAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return  R.layout.frg_company;
    }

    @Override
    protected void bindViews(View view) {
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CompanyAdapter(new ArrayList());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void processLogic() {
//        mPresenter.getSpecialData();
        initTestData();
    }

    private void initTestData(){
        List<String> mProductList = new ArrayList<>();
        for (int i=0; i<10; i++){
            mProductList.add(ProjectConstants.TEST_IMAGE);
        }
        mAdapter.setNewData(mProductList);
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
    }



    @Override
    protected SpecialPresenter createPresenter() {
        return new SpecialPresenter();
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        mPresenter.getSpecialData();
    }


    @Override
    public void getSpecialData(SpecialData result) {

    }
}
