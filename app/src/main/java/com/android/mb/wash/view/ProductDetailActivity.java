package com.android.mb.wash.view;

import android.app.Activity;
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
import com.android.mb.wash.entity.ImageBean;
import com.android.mb.wash.entity.ProductBean;
import com.android.mb.wash.presenter.ProductDetailPresenter;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.ImageUtils;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.view.interfaces.IProductDetailView;
import com.android.mb.wash.widget.CustomImageLayout;
import com.android.mb.wash.widget.MyImageLayout;
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
    private List<ImageBean> mImageList = new ArrayList<ImageBean>();

    private RecyclerView mRecyclerView;
    private DescAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ProductBean mProductBean;

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
        mProductBean = result;
        mImageList = new ArrayList<>();
        if (Helper.isNotEmpty(result.getVideoCover())) {
            mImageList.add(0,new ImageBean(1,result.getVideoCover(),result.getVideoUrl()));
        }
        if (Helper.isNotEmpty(result.getImageUrls())){
            for (String imageUrl:result.getImageUrls()) {
                mImageList.add(new ImageBean(0,imageUrl,imageUrl));
            }
        }
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
        ImageBean imageBean = mImageList.get(position);
        if (imageBean.getImageType()==1){
            Bundle bundle = new Bundle();
            bundle.putString("videoUrl",imageBean.getVideoUrl());
            NavigationHelper.startActivity((Activity) mContext, PlayVideoActivity.class,bundle,false);
        } else {
            ImagePreview.getInstance()
                    .setContext(mContext)
                    .setIndex(position)
                    .setShowDownButton(false)
                    .setImageList(mProductBean.getImageUrls())
                    .start();
        }
    }

    public class ProductImageLoader extends CustomImageLayout {

        @Override
        public void displayImage(Context context, Object path, MyImageLayout imageLayout) {
            ImageBean imageBean = (ImageBean) path;
            imageLayout.showPlay(imageBean.getImageType() == 1);
            imageLayout.loadImage(imageBean.getImageUrl());
        }
    }
}
