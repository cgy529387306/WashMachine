package com.android.mb.wash.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.wash.R;
import com.android.mb.wash.adapter.CompanyAdapter;
import com.android.mb.wash.base.BaseMvpFragment;
import com.android.mb.wash.entity.ResourceListData;
import com.android.mb.wash.presenter.ResourceListPresenter;
import com.android.mb.wash.utils.AppHelper;
import com.android.mb.wash.view.interfaces.IResourceListView;
import com.android.mb.wash.widget.MyDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by cgy on 16/7/18.
 */
public class CompanyFragment extends BaseMvpFragment<ResourceListPresenter, IResourceListView> implements IResourceListView, View.OnClickListener,BaseQuickAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener {

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
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(LinearLayoutManager.VERTICAL, Color.parseColor("#F7F7F7"), AppHelper.calDpi2px(10)));
        mAdapter = new CompanyAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void processLogic() {
        getListFormServer();
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
    protected ResourceListPresenter createPresenter() {
        return new ResourceListPresenter();
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        getListFormServer();
    }


    @Override
    public void getSuccess(ResourceListData result) {
        if (result!=null){
            mRefreshLayout.finishRefresh();
            mRefreshLayout.finishLoadMoreWithNoMoreData();
            mAdapter.setNewData(result.getList());
            mAdapter.setEmptyView(R.layout.empty_data, (ViewGroup) mRecyclerView.getParent());
        }
    }

    private void getListFormServer(){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("type",1);
        mPresenter.getList(requestMap);
    }
}
