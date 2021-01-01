package com.android.mb.wash.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.android.mb.wash.R;
import com.android.mb.wash.adapter.ProductListAdapter;
import com.android.mb.wash.base.BaseMvpActivity;
import com.android.mb.wash.constants.ProjectConstants;
import com.android.mb.wash.entity.Advert;
import com.android.mb.wash.entity.Category;
import com.android.mb.wash.entity.ProductBean;
import com.android.mb.wash.entity.ProductListData;
import com.android.mb.wash.presenter.ProductListPresenter;
import com.android.mb.wash.utils.AppHelper;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.utils.ToastHelper;
import com.android.mb.wash.view.interfaces.IProductListView;
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

/**
 * Created by cgy on 2018\8\20 0020.
 */

public class SearchActivity extends BaseMvpActivity<ProductListPresenter, IProductListView> implements IProductListView, View.OnClickListener,BaseQuickAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener {

    private EditText mEtSearch;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private ProductListAdapter mAdapter;
    private int mCurrentPage = 1;
    @Override
    protected void loadIntent() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initTitle() {
        hideActionbar();
    }


    @Override
    protected void bindViews() {
        mEtSearch = findViewById(R.id.et_search);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, AppHelper.calDpi2px(10), true));
        mAdapter = new ProductListAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(false);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
    }


    @Override
    protected void setListener() {
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(this);
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                String keyword = mEtSearch.getText().toString();
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    if (Helper.isEmpty(keyword)){
                        ToastHelper.showToast("请输入搜索内容");
                    }else {
                        mCurrentPage =1;
                        getListFormServer();
                    }
                    return true;
                }
                return false;
            }
        });
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String keyword = mEtSearch.getText().toString();
                if (Helper.isEmpty(keyword)){
                    mAdapter.getData().clear();
                    mAdapter.notifyDataSetChanged();
                }else {
                    mCurrentPage =1;
                    getListFormServer();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_cancel){
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        AppHelper.hideSoftInputFromWindow(mEtSearch);
        super.onBackPressed();
    }

    @Override
    protected ProductListPresenter createPresenter() {
        return new ProductListPresenter();
    }


    private void getListFormServer(){
        String keyword = mEtSearch.getText().toString();
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("keyword", keyword);
        requestMap.put("currentPage",mCurrentPage);
        requestMap.put("pageSize", ProjectConstants.PAGE_SIZE);
        mPresenter.getProductList(requestMap);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ProductBean productBean = mAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putString("productId",productBean.getId());
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
    public void getAdSuccess(List<Advert> result) {

    }

    @Override
    public void getProductSuccess(ProductListData result) {
        if (result!=null){
            mRefreshLayout.setEnableLoadMore(true);
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
