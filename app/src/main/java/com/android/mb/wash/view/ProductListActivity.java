package com.android.mb.wash.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.wash.R;
import com.android.mb.wash.adapter.ProductListAdapter;
import com.android.mb.wash.base.BaseMvpActivity;
import com.android.mb.wash.constants.ProjectConstants;
import com.android.mb.wash.entity.Category;
import com.android.mb.wash.entity.ProductBean;
import com.android.mb.wash.entity.ProductListData;
import com.android.mb.wash.entity.ProductType;
import com.android.mb.wash.entity.VideoListData;
import com.android.mb.wash.presenter.ProductListPresenter;
import com.android.mb.wash.presenter.SearchPresenter;
import com.android.mb.wash.utils.AppHelper;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.utils.TestHelper;
import com.android.mb.wash.view.interfaces.IProductListView;
import com.android.mb.wash.view.interfaces.ISearchView;
import com.android.mb.wash.widget.GridSpacingItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.shinichi.library.ImagePreview;

/**
 * Created by cgy on 2018\8\20 0020.
 */

public class ProductListActivity extends BaseMvpActivity<ProductListPresenter, IProductListView> implements IProductListView,View.OnClickListener,BaseQuickAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private ProductListAdapter mAdapter;
    private int mCurrentPage = 1;
    private ProductType mProductType;
    @Override
    protected void loadIntent() {
        mProductType = (ProductType) getIntent().getSerializableExtra("productType");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_recycleview;
    }

    @Override
    protected void initTitle() {
        setTitleText(mProductType==null?"":mProductType.getTypeName());
    }


    @Override
    protected void bindViews() {
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, AppHelper.calDpi2px(10), true));
        mAdapter = new ProductListAdapter(new ArrayList());
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
        int id = v.getId();
    }


    @Override
    protected ProductListPresenter createPresenter() {
        return new ProductListPresenter();
    }


    private void getListFormServer(){
        if (mProductType != null){
            Map<String,Object> requestMap = new HashMap<>();
            requestMap.put("currentPage",mCurrentPage);
            requestMap.put("pageSize", ProjectConstants.PAGE_SIZE);
            requestMap.put("typeId",mProductType.getTypeId());
            mPresenter.getProductList(requestMap);
        }
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ProductBean productBean = mAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("productBean",productBean);
        NavigationHelper.startActivity(mContext, ProductDetailActivity.class,bundle,false);
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
    public void getSuccess(List<Category> result) {

    }

    @Override
    public void getProductSuccess(ProductListData result) {
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
}
