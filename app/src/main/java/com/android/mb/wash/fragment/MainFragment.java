package com.android.mb.wash.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.android.mb.wash.R;
import com.android.mb.wash.adapter.HomeAdapter;
import com.android.mb.wash.base.BaseMvpFragment;
import com.android.mb.wash.entity.Advert;
import com.android.mb.wash.entity.HomeData;
import com.android.mb.wash.entity.HomeItem;
import com.android.mb.wash.presenter.HomePresenter;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.ImageUtils;
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
import java.util.List;


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
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        mAdapter = new HomeAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);

        //添加Header
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.item_home_header, mRecyclerView, false);
        mBanner = header.findViewById(R.id.bannerView);
        mAdapter.addHeaderView(header);
    }

    @Override
    protected void processLogic() {
//        mPresenter.getHomeData();
        initTestData();
    }

    private void initTestData(){
        mAdvertList.add(new Advert("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1626888615,3982821741&fm=26&gp=0.jpg"));
        mAdvertList.add(new Advert("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1599302968195&di=b4c77b69f0a5caf9f4992c9a6d487fea&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F01%2F37%2F18%2F82%2Fs_1024_f0c29b9289ee968b02b7a417081d5a55.jpg"));
        mAdvertList.add(new Advert("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1599302968193&di=8133d602217a69b329c1696856797b14&imgtype=0&src=http%3A%2F%2Fimg1.99114.com%2Fgroup1%2FM00%2F7D%2F61%2FwKgGTFe_9mSALe5uAAE_Flfxnt8396.jpg"));
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setImages(mAdvertList);
        mBanner.start();

        List<HomeItem> dataList = new ArrayList<>();
        dataList.add(new HomeItem(HomeItem.POST));
        dataList.add(new HomeItem(HomeItem.NEW));
        dataList.add(new HomeItem(HomeItem.HOT));
        mAdapter.setNewData(dataList);
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
        mPresenter.getHomeData();
    }

    @Override
    public void getHomeData(HomeData homeData) {
    }

    @Override
    public void OnBannerClick(int position) {
        if (Helper.isNotEmpty(mAdvertList) && mAdvertList.size()>position){
        }
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            ImageUtils.loadImageUrlDark(imageView,((Advert)path).getCoverUrl());
        }
    }

}
