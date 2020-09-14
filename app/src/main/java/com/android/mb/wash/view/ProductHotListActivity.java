package com.android.mb.wash.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.mb.wash.R;
import com.android.mb.wash.adapter.ProductListAdapter;
import com.android.mb.wash.base.BaseMvpActivity;
import com.android.mb.wash.entity.VideoListData;
import com.android.mb.wash.presenter.SearchPresenter;
import com.android.mb.wash.utils.TestHelper;
import com.android.mb.wash.view.interfaces.ISearchView;
import com.android.mb.wash.widget.MyDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import cc.shinichi.library.ImagePreview;

/**
 * Created by cgy on 2018\8\20 0020.
 */

public class ProductHotListActivity extends BaseMvpActivity<SearchPresenter,
        ISearchView> implements ISearchView,View.OnClickListener,BaseQuickAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private ProductListAdapter mAdapter;
    private int mCurrentPage = 1;
    @Override
    protected void loadIntent() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_recycleview;
    }

    @Override
    protected void initTitle() {
        setTitleText("热卖商品");
    }


    @Override
    protected void bindViews() {
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mAdapter = new ProductListAdapter(TestHelper.getTestImage());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
//        getListFormServer();
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
    protected SearchPresenter createPresenter() {
        return new SearchPresenter();
    }

    @Override
    public void getSuccess(VideoListData result) {
    }


    private void getListFormServer(){
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //多图加载时图片地址要不一样
        ImagePreview.getInstance()
                // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好；
                .setContext(ProductHotListActivity.this)
                // 设置从第几张开始看（索引从0开始）
                .setIndex(position)
                // 2：直接传url List
                .setImageList(mAdapter.getData())
                .setShowCloseButton(true)
                // 3：只有一张图片的情况，可以直接传入这张图片的url
                //.setImage(String image)
                // 开启预览
                .start();
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

}
