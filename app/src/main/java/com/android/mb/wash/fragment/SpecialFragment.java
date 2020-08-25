package com.android.mb.wash.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.mb.wash.R;
import com.android.mb.wash.adapter.MovieRecycleAdapter;
import com.android.mb.wash.adapter.SpecialAdapter;
import com.android.mb.wash.base.BaseMvpFragment;
import com.android.mb.wash.entity.AuthorVideo;
import com.android.mb.wash.entity.SpecialData;
import com.android.mb.wash.presenter.SpecialPresenter;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.view.VideoListActivity;
import com.android.mb.wash.view.interfaces.ISpecialView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;


/**
 * Created by cgy on 16/7/18.
 */
public class SpecialFragment extends BaseMvpFragment<SpecialPresenter,ISpecialView> implements ISpecialView, View.OnClickListener,BaseQuickAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private MovieRecycleAdapter mAdapter;
    private GridView mGridCate;
    @Override
    protected int getLayoutId() {
        return  R.layout.common_recycleview;
    }

    @Override
    protected void bindViews(View view) {
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MovieRecycleAdapter(R.layout.item_movie_recycle, new ArrayList());
        mRecyclerView.setAdapter(mAdapter);

        //添加Header
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.item_special_header, mRecyclerView, false);
        mGridCate = header.findViewById(R.id.gridCate);
        mAdapter.addHeaderView(header);
    }

    @Override
    protected void processLogic() {
        mPresenter.getSpecialData();
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        AuthorVideo authorVideo = mAdapter.getItem(position);
        if (authorVideo!=null){
            String name = authorVideo.getName();
            Bundle bundle = new Bundle();
            bundle.putString("name",name);
            bundle.putString("authorId",authorVideo.getId());
            NavigationHelper.startActivity(getActivity(), VideoListActivity.class,bundle,false);
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
    }



    @Override
    protected SpecialPresenter createPresenter() {
        return new SpecialPresenter();
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        mPresenter.getSpecialData();
    }


    @Override
    public void getSpecialData(SpecialData result) {
        if (result!=null && result.getAuthorList()!=null){
            mRefreshLayout.finishRefresh();
            mAdapter.setNewData(result.getAuthorList());
            mAdapter.setEmptyView(R.layout.empty_data, (ViewGroup) mRecyclerView.getParent());
            mRefreshLayout.finishLoadMoreWithNoMoreData();
            if (mGridCate!=null && Helper.isNotEmpty(result.getSpecialList())){
                mGridCate.setAdapter(new SpecialAdapter(getActivity(),result.getSpecialList()));
            }
        }
    }
}
