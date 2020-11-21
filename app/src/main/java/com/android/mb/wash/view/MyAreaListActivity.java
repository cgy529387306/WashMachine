package com.android.mb.wash.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.wash.R;
import com.android.mb.wash.adapter.AreaAdapter;
import com.android.mb.wash.base.BaseMvpActivity;
import com.android.mb.wash.constants.ProjectConstants;
import com.android.mb.wash.entity.AreaBean;
import com.android.mb.wash.entity.AreaListData;
import com.android.mb.wash.presenter.AreaListPresenter;
import com.android.mb.wash.rxbus.Events;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.view.interfaces.IAreaListView;
import com.android.mb.wash.widget.MyDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;

/**
 * Created by cgy on 2018\8\20 0020.
 */

public class MyAreaListActivity extends BaseMvpActivity<AreaListPresenter,
        IAreaListView> implements IAreaListView,View.OnClickListener,BaseQuickAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private AreaAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void loadIntent() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_area;
    }

    @Override
    protected void initTitle() {
        setTitleText("专卖店");
    }


    @Override
    protected void bindViews() {
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(LinearLayoutManager.VERTICAL));
        mRefreshLayout.setEnableLoadMore(false);
        mAdapter = new AreaAdapter(R.layout.item_area, new ArrayList());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        getListFormServer();
        regiestEvent(ProjectConstants.EVENT_UPDATE_POST, new Action1<Events<?>>() {
            @Override
            public void call(Events<?> events) {
                onRefresh(null);
            }
        });
    }


    @Override
    protected void setListener() {
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
    }


    @Override
    protected AreaListPresenter createPresenter() {
        return new AreaListPresenter();
    }


    private void getListFormServer(){
        Map<String,Object> requestMap = new HashMap<>();
        mPresenter.getList(requestMap);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        AreaBean areaBean = mAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putInt("type",4);
        bundle.putSerializable("areaBean",areaBean);
        NavigationHelper.startActivity(mContext, ResourceActivity.class,bundle,false);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getListFormServer();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getListFormServer();
    }

    @Override
    public void getSuccess(List<AreaBean> result) {
        if (result!=null) {
            mRefreshLayout.finishRefresh();
            mAdapter.setNewData(result);
            mAdapter.setEmptyView(R.layout.empty_data, (ViewGroup) mRecyclerView.getParent());
        }
    }
}
