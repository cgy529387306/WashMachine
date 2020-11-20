package com.android.mb.wash.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.mb.wash.R;
import com.android.mb.wash.adapter.HomeAdapter;
import com.android.mb.wash.base.BaseMvpFragment;
import com.android.mb.wash.constants.ProjectConstants;
import com.android.mb.wash.entity.Advert;
import com.android.mb.wash.entity.HomeData;
import com.android.mb.wash.entity.HomeItem;
import com.android.mb.wash.presenter.HomePresenter;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.ImageUtils;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.view.SearchActivity;
import com.android.mb.wash.view.interfaces.IHomeView;
import com.android.mb.wash.widget.MyDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by cgy on 16/7/18.
 */
public class MainFragment extends BaseMvpFragment<HomePresenter,IHomeView> implements IHomeView, OnBannerListener,View.OnClickListener,BaseQuickAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private Banner mBanner;
    private List<Advert> mAdvertList = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return  R.layout.frg_main;
    }

    @Override
    protected void bindViews(View view) {
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(LinearLayoutManager.VERTICAL));
        mAdapter = new HomeAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);

        //添加Header
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.item_home_header, mRecyclerView, false);
        mBanner = header.findViewById(R.id.bannerView);
        mAdapter.addHeaderView(header);
    }

    @Override
    protected void processLogic() {
        getListFormServer();
    }

    @Override
    protected void setListener() {
        mRootView.findViewById(R.id.tv_search).setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
        mBanner.setOnBannerListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_search){
            NavigationHelper.startActivity(getActivity(), SearchActivity.class,null,false);
        }
    }


    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }


    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        getListFormServer();
    }

    @Override
    public void getHomeData(HomeData homeData) {
        if (homeData!=null){
            mRefreshLayout.finishRefresh();
            mRefreshLayout.finishLoadMoreWithNoMoreData();
            List<HomeItem> dataList = new ArrayList<>();
            HomeItem postItem = new HomeItem(HomeItem.POST);
            postItem.setPostBeanList(homeData.getDynamicList());
            dataList.add(postItem);
            HomeItem productItem = new HomeItem(HomeItem.PRODUCT);
            productItem.setProductTypeList(homeData.getProductList());
            dataList.add(productItem);
            mAdapter.setNewData(dataList);
            if (mBanner!=null && Helper.isNotEmpty(homeData.getAdvertList())){
                mAdvertList = homeData.getAdvertList();
                mBanner.setImageLoader(new GlideImageLoader());
                mBanner.setImages(mAdvertList);
                mBanner.start();
            }
        }
    }

    @Override
    public void OnBannerClick(int position) {
        if (Helper.isNotEmpty(mAdvertList) && mAdvertList.size()>position){
        }
    }

    private void getListFormServer(){
        Map<String,Object> requestMap = new HashMap<>();
        mPresenter.getHomeData(requestMap);
    }


    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            ImageUtils.loadImageUrl(imageView,((Advert)path).getCoverUrl());
        }
    }

}
