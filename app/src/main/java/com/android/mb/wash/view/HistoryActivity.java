package com.android.mb.wash.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.wash.R;
import com.android.mb.wash.adapter.MovieListAdapter;
import com.android.mb.wash.base.BaseMvpActivity;
import com.android.mb.wash.constants.ProjectConstants;
import com.android.mb.wash.entity.Video;
import com.android.mb.wash.entity.VideoListData;
import com.android.mb.wash.presenter.HistoryPresenter;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.utils.ToastHelper;
import com.android.mb.wash.view.interfaces.IHistoryView;
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

/**
 * Created by cgy on 2018\8\20 0020.
 */

public class HistoryActivity extends BaseMvpActivity<HistoryPresenter,
        IHistoryView> implements IHistoryView,View.OnClickListener,BaseQuickAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private MovieListAdapter mAdapter;
    private int mCurrentPage = 1;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean mIsEdit;
    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_recycleview;
    }

    @Override
    protected void initTitle() {
        setTitleText("历史记录");
    }

    @Override
    protected void onRightAction() {
        super.onRightAction();
        if (mIsEdit){
            if (Helper.isEmpty(getVideoIds())){
                ToastHelper.showToast("请选择删除项");
            }else{
                deleteHistory();
            }
        }else{
            mIsEdit = true;
            setRightText("删除");
            mAdapter.setCanEdit(true);
        }
    }

    @Override
    protected void bindViews() {
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(this,MyDividerItemDecoration.VERTICAL_LIST));
        mAdapter = new MovieListAdapter(R.layout.item_movie_list, new ArrayList());
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
    protected HistoryPresenter createPresenter() {
        return new HistoryPresenter();
    }

    @Override
    public void getSuccess(VideoListData result) {
        if (result!=null){
            if (result.isEnd()){
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            }
            if (mCurrentPage == 1){
                setRightText(result.getRowCount()==0?"":"编辑");
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

    @Override
    public void deleteSuccess(Object result) {
        mCurrentPage = 1;
        getListFormServer();
        setRightText("编辑");
        mIsEdit = false;
        mAdapter.setCanEdit(false);
        ToastHelper.showToast("删除成功");
    }

    private void getListFormServer(){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("currentPage",mCurrentPage);
        requestMap.put("pageSize", ProjectConstants.PAGE_SIZE);
        mPresenter.getHistory(requestMap);
    }

    private void deleteHistory(){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("videoIds",getVideoIds());
        mPresenter.delHistory(requestMap);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Video video = mAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("videoInfo",video);
        NavigationHelper.startActivity( mContext, DetailActivity.class,bundle,false);
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

    private String getVideoIds(){
        StringBuilder sb = new StringBuilder();
        List<Video> dataList = mAdapter.getData();
        for (Video video:dataList){
            if (video.isSelect()){
                sb.append(video.getId()).append(",");
            }
        }
        return sb.toString();
    }
}
