package com.android.mb.wash.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.wash.R;
import com.android.mb.wash.adapter.AreaAdapter;
import com.android.mb.wash.adapter.DescAdapter;
import com.android.mb.wash.base.BaseMvpActivity;
import com.android.mb.wash.entity.ProductBean;
import com.android.mb.wash.presenter.ProductDetailPresenter;
import com.android.mb.wash.utils.ImageUtils;
import com.android.mb.wash.view.interfaces.IProductDetailView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.shinichi.library.ImagePreview;

public class ProductDetailActivity extends BaseMvpActivity<ProductDetailPresenter, IProductDetailView> implements IProductDetailView, OnBannerListener, View.OnClickListener{

    private Banner mBanner;
    private TextView mTvPrice,mTvDesc,mTvContent;
    private String mProductId;
    private List<String> mImageList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private DescAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected ProductDetailPresenter createPresenter() {
        return new ProductDetailPresenter();
    }

    @Override
    protected void loadIntent() {
        mProductId = getIntent().getStringExtra("productId");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void initTitle() {
        setTitleText("产品详情");
    }

    @Override
    protected void bindViews() {
        mBanner = findViewById(R.id.bannerView);
        mTvPrice = findViewById(R.id.tv_price);
        mTvDesc = findViewById(R.id.tv_desc);
        mTvContent = findViewById(R.id.tv_content);

        mRecyclerView = findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new DescAdapter(R.layout.item_product_desc, new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
        RichText.initCacheDir(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        getDataFormServer();
    }

    @Override
    protected void setListener() {
        mBanner.setOnBannerListener(this);
    }

    private void getDataFormServer(){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("productId",mProductId);
        mPresenter.getDetail(requestMap);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
    }



    @Override
    public void getDetail(ProductBean result) {
        mImageList = result.getImageUrls();
        mBanner.setImageLoader(new ProductImageLoader());
        mBanner.setImages(mImageList);
        mBanner.start();

        mTvPrice.setText("¥" + result.getPrice());
        mTvDesc.setText(result.getName());
        mAdapter.setNewData(result.getParamList());
        RichText.from(result.getContent()).into(mTvContent);
    }

    @Override
    public void OnBannerClick(int position) {
        ImagePreview.getInstance()
                .setContext(mContext)
                .setIndex(position)
                .setShowDownButton(false)
                .setImageList(mImageList)
                .start();
    }

    public class ProductImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            ImageUtils.loadImageUrl(imageView,((String)path));
        }
    }
}
