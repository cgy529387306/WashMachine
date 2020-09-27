package com.android.mb.wash.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.wash.R;
import com.android.mb.wash.adapter.ProductAdapter;
import com.android.mb.wash.adapter.ProductCateAdapter;
import com.android.mb.wash.base.BaseMvpFragment;
import com.android.mb.wash.constants.ProjectConstants;
import com.android.mb.wash.entity.Category;
import com.android.mb.wash.entity.ProductListData;
import com.android.mb.wash.presenter.ProductListPresenter;
import com.android.mb.wash.utils.AppHelper;
import com.android.mb.wash.utils.Helper;
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
 * Created by cgy on 16/7/18.
 */
public class ProductFragment extends BaseMvpFragment<ProductListPresenter, IProductListView> implements IProductListView, View.OnClickListener,BaseQuickAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener {
    private RecyclerView mRvCate;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private ProductAdapter mAdapter;
    private ProductCateAdapter mCateAdapter;
    private int mCurrentPage = 1;

    @Override
    protected int getLayoutId() {
        return  R.layout.frg_product;
    }

    @Override
    protected void bindViews(View view) {
        mRvCate = view.findViewById(R.id.rv_cate);
        mRvCate.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCateAdapter = new ProductCateAdapter(new ArrayList<>());
        mRvCate.setAdapter(mCateAdapter);

        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, AppHelper.calDpi2px(10), true));
        mAdapter = new ProductAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void processLogic() {
        getCategoryList();
    }


    @Override
    protected void setListener() {
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
        mCateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mCateAdapter.setSelectPos(position);
                onRefresh(null);
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
    }



    @Override
    protected ProductListPresenter createPresenter() {
        return new ProductListPresenter();
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
        if (Helper.isNotEmpty(result)){
            mCateAdapter.setNewData(result);
            onRefresh(null);
        }
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

    private void getCategoryList(){
        Map<String,Object> requestMap = new HashMap<>();
        mPresenter.getCategoryList(requestMap);
    }

    private void getListFormServer(){
        Category category = mCateAdapter.getSelectCategory();
        if (category != null){
            Map<String,Object> requestMap = new HashMap<>();
            requestMap.put("currentPage",mCurrentPage);
            requestMap.put("pageSize", ProjectConstants.PAGE_SIZE);
            requestMap.put("cateId",category.getCateId());
            mPresenter.getProductList(requestMap);
        }
    }

}
