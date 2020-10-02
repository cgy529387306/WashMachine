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
import com.android.mb.wash.entity.ProductListData;
import com.android.mb.wash.entity.ProductType;
import com.android.mb.wash.entity.VideoListData;
import com.android.mb.wash.presenter.ProductListPresenter;
import com.android.mb.wash.presenter.SearchPresenter;
import com.android.mb.wash.utils.AppHelper;
import com.android.mb.wash.utils.Helper;
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
        //多图加载时图片地址要不一样
//        ImagePreview.getInstance()
//                // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好；
//                .setContext(ProductListActivity.this)
//                // 设置从第几张开始看（索引从0开始）
//                .setIndex(position)
//                // 2：直接传url List
//                .setImageList(mAdapter.getData())
//                .setShowCloseButton(true)
//                // 3：只有一张图片的情况，可以直接传入这张图片的url
//                //.setImage(String image)
//                // 开启预览
//                .start();
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
