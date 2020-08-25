package com.android.mb.wash.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.wash.R;
import com.android.mb.wash.adapter.MovieListAdapter;
import com.android.mb.wash.adapter.TagAdapter;
import com.android.mb.wash.base.BaseMvpFragment;
import com.android.mb.wash.constants.ProjectConstants;
import com.android.mb.wash.entity.Tag;
import com.android.mb.wash.entity.Video;
import com.android.mb.wash.entity.VideoListData;
import com.android.mb.wash.presenter.TagPresenter;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.view.DetailActivity;
import com.android.mb.wash.view.interfaces.ITagView;
import com.android.mb.wash.widget.MyDividerItemDecoration;
import com.android.mb.wash.widget.taglayout.FlowTagLayout;
import com.android.mb.wash.widget.taglayout.OnTagSelectListener;
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
public class TagFragment extends BaseMvpFragment<TagPresenter,ITagView> implements ITagView,BaseQuickAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener {
    private FlowTagLayout mTagHot;
    private TagAdapter<Tag> mHotAdapter;

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private MovieListAdapter mAdapter;
    private int mCurrentPage = 1;
    private LinearLayoutManager mLinearLayoutManager;
    private String mTags;
    private boolean mIsFisrt;

    @Override
    protected int getLayoutId() {
        return  R.layout.frg_tag;
    }

    @Override
    protected void bindViews(View view) {
        mTagHot = mRootView.findViewById(R.id.tagLayout);
        mHotAdapter = new TagAdapter<>(getActivity());
        mTagHot.setAdapter(mHotAdapter);
        mTagHot.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        mTagHot.clearAllOption();

        mRefreshLayout =  mRootView.findViewById(R.id.refreshLayout);
        mRecyclerView =  mRootView.findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(mContext,MyDividerItemDecoration.VERTICAL_LIST));
        mAdapter = new MovieListAdapter(R.layout.item_movie_list, new ArrayList());
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setEnableRefresh(false);
    }

    @Override
    protected void processLogic() {
        mPresenter.getTags();
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(this);
        mTagHot.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                if (selectedList != null && selectedList.size() > 0) {
                    int position = selectedList.get(0);
                    Tag tag = (Tag) mTagHot.getAdapter().getItem(position);
                    mTags = tag.getId();
                    if (mIsFisrt){
                        mIsFisrt = false;
                    }else{
                        showProgressDialog();
                    }
                    onRefresh(null);
                }
            }
        });
    }


    @Override
    protected TagPresenter createPresenter() {
        return new TagPresenter();
    }

    @Override
    public void getSuccess(List<Tag> result) {
        mHotAdapter.clearAndAddAll(result);
        mTagHot.clearAllOption();
        if (Helper.isNotEmpty(result)){
            mIsFisrt = true;
            mTagHot.setSelected(0);
        }
    }

    @Override
    public void querySuccess(VideoListData result) {
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
        requestMap.put("currentPage",1);
        requestMap.put("pageSize", ProjectConstants.PAGE_SIZE);
        requestMap.put("tags", mTags);
        mPresenter.queryVideos(requestMap);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Video video = mAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("videoInfo",video);
        NavigationHelper.startActivity(mContext, DetailActivity.class,bundle,false);
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
