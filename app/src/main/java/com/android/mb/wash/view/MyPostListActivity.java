package com.android.mb.wash.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.wash.R;
import com.android.mb.wash.adapter.PostAdapter;
import com.android.mb.wash.base.BaseMvpActivity;
import com.android.mb.wash.constants.ProjectConstants;
import com.android.mb.wash.entity.PostBean;
import com.android.mb.wash.entity.PostListData;
import com.android.mb.wash.presenter.PostListPresenter;
import com.android.mb.wash.rxbus.Events;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.view.interfaces.IPostListView;
import com.android.mb.wash.widget.MyDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

/**
 * Created by cgy on 2018\8\20 0020.
 */

public class MyPostListActivity extends BaseMvpActivity<PostListPresenter,
        IPostListView> implements IPostListView,View.OnClickListener,BaseQuickAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private PostAdapter mAdapter;
    private int mCurrentPage = 1;
    private LinearLayoutManager mLinearLayoutManager;
    private int mType;

    @Override
    protected void loadIntent() {
        mType = getIntent().getIntExtra("type",1);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_recycleview;
    }

    @Override
    protected void initTitle() {
        setTitleText(mType == 2 ? "我的点赞":"我的动态");
    }


    @Override
    protected void bindViews() {
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(LinearLayoutManager.VERTICAL));
        mAdapter = new PostAdapter(new ArrayList());
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
    protected PostListPresenter createPresenter() {
        return new PostListPresenter();
    }


    private void getListFormServer(){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("currentPage",mCurrentPage);
        requestMap.put("pageSize", ProjectConstants.PAGE_SIZE);
        requestMap.put("type", mType);
        mPresenter.getPostList(requestMap);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PostBean postBean = mAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("postBean",postBean);
        NavigationHelper.startActivity(mContext, PostDetailActivity.class,bundle,false);
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
    public void getSuccess(PostListData result) {
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
