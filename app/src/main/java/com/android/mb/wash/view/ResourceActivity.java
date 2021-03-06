package com.android.mb.wash.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.wash.R;
import com.android.mb.wash.adapter.ResourceAdapter;
import com.android.mb.wash.base.BaseMvpActivity;
import com.android.mb.wash.constants.ProjectConstants;
import com.android.mb.wash.entity.AreaBean;
import com.android.mb.wash.entity.ResourceBean;
import com.android.mb.wash.entity.ResourceListData;
import com.android.mb.wash.presenter.ResourceListPresenter;
import com.android.mb.wash.utils.AppHelper;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.utils.ProjectHelper;
import com.android.mb.wash.view.interfaces.IResourceListView;
import com.android.mb.wash.widget.GridSpacingItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cc.shinichi.library.ImagePreview;

public class ResourceActivity extends BaseMvpActivity<ResourceListPresenter, IResourceListView> implements IResourceListView, View.OnClickListener, BaseQuickAdapter.OnItemClickListener, OnRefreshListener, OnLoadMoreListener {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private ResourceAdapter mAdapter;
    private int mCurrentPage = 1;
    private int mType = 1;
    private AreaBean mAreaBean;


    @Override
    protected void loadIntent() {
        mType = getIntent().getIntExtra("type", 1);
        mAreaBean = (AreaBean) getIntent().getSerializableExtra("areaBean");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_recycleview;
    }

    @Override
    protected void initTitle() {
        setTitleText(ProjectHelper.getResourceTitle(mType,mAreaBean));
    }

    @Override
    protected void bindViews() {
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, AppHelper.calDpi2px(10), true));
        mAdapter = new ResourceAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        getListFormServer();
    }


    @Override
    protected void setListener() {
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    public SmartRefreshLayout getRefreshLayout() {
        return mRefreshLayout;
    }

    public void setRefreshLayout(SmartRefreshLayout refreshLayout) {
        mRefreshLayout = refreshLayout;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ResourceBean resourceBean = mAdapter.getItem(position);
        boolean isVideo = ProjectHelper.isVideo(resourceBean.getResUrl());
        if (isVideo) {
            Bundle bundle = new Bundle();
            bundle.putString("videoUrl",resourceBean.getResUrl());
            NavigationHelper.startActivity(mContext, PlayVideoActivity.class,bundle,false);
        } else {
            ImagePreview.getInstance()
                    .setContext(mContext)
                    .setShowDownButton(false)
                    .setImage(resourceBean.getResUrl())
                    .start();
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
        getListFormServer();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 1;
        getListFormServer();
    }

    @Override
    protected ResourceListPresenter createPresenter() {
        return new ResourceListPresenter();
    }

    @Override
    public void getSuccess(ResourceListData result) {
        if (result!=null){
            if (result.isEnd()){
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            }
            if (mCurrentPage == 1){
                mRefreshLayout.finishRefresh();
                mAdapter.setNewData(result.getList());
                mAdapter.setEmptyView(R.layout.empty_data, (ViewGroup) mRecyclerView.getParent());
            }else{
                if (Helper.isEmpty(result)){
                    mRefreshLayout.finishLoadMoreWithNoMoreData();
                }else{
                    mAdapter.addData(result.getList());
                    mRefreshLayout.finishLoadMore();
                }
            }
        }
    }

    private void getListFormServer(){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("currentPage",mCurrentPage);
        requestMap.put("pageSize", ProjectConstants.PAGE_SIZE);
        requestMap.put("type",mType);
        if (mAreaBean!=null && Helper.isNotEmpty(mAreaBean.getId())){
            requestMap.put("areaId",mAreaBean.getId());
        }
        mPresenter.getList(requestMap);
    }

}