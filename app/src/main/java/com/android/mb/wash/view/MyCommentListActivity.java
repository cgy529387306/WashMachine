package com.android.mb.wash.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.mb.wash.R;
import com.android.mb.wash.adapter.CommentAdapter;
import com.android.mb.wash.base.BaseMvpActivity;
import com.android.mb.wash.constants.ProjectConstants;
import com.android.mb.wash.entity.Comment;
import com.android.mb.wash.entity.CommentListData;
import com.android.mb.wash.presenter.CommentListPresenter;
import com.android.mb.wash.rxbus.Events;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.utils.ToastHelper;
import com.android.mb.wash.view.interfaces.ICommentListView;
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

public class MyCommentListActivity extends BaseMvpActivity<CommentListPresenter,
        ICommentListView> implements ICommentListView,View.OnClickListener,BaseQuickAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener, BaseQuickAdapter.OnItemLongClickListener {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private CommentAdapter mAdapter;
    private int mCurrentPage = 1;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void loadIntent() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_recycleview;
    }

    @Override
    protected void initTitle() {
        setTitleText("我的评论");
    }


    @Override
    protected void bindViews() {
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(LinearLayoutManager.VERTICAL));
        mAdapter = new CommentAdapter(R.layout.item_comment, new ArrayList());
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
        mAdapter.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
    }


    @Override
    protected CommentListPresenter createPresenter() {
        return new CommentListPresenter();
    }


    private void getListFormServer(){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("currentPage",mCurrentPage);
        requestMap.put("pageSize", ProjectConstants.PAGE_SIZE);
        mPresenter.getPostComments(requestMap);
    }

    private void delete(String id){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("commentIds", id);
        mPresenter.deleteComment(requestMap);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Comment comment = mAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dynamicId",comment.getDynamicId());
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
    public void getSuccess(CommentListData result) {
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

    @Override
    public void deleteSuccess(Object result) {
        ToastHelper.showLongToast("删除成功");
        onRefresh(null);
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        new MaterialDialog.Builder(mContext).title("提示").content("确定要删除该条评论？")
                .positiveText("确定").negativeText("取消").onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                Comment comment = mAdapter.getItem(position);
                delete(comment.getId());
            }
        }).show();
        return false;
    }
}
