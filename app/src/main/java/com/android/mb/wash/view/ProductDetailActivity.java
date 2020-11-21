package com.android.mb.wash.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.wash.R;
import com.android.mb.wash.base.BaseMvpActivity;
import com.android.mb.wash.entity.ProductBean;
import com.android.mb.wash.presenter.ProductDetailPresenter;
import com.android.mb.wash.utils.ImageUtils;
import com.android.mb.wash.view.interfaces.IProductDetailView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.HashMap;
import java.util.Map;

public class ProductDetailActivity extends BaseMvpActivity<ProductDetailPresenter, IProductDetailView> implements IProductDetailView, View.OnClickListener{

    private Banner mBanner;
    private TextView mTvPrice,mTvDesc,mTvCate;
    private String mProductId;

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
        mTvCate = findViewById(R.id.tv_cate);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        getDataFormServer();
    }

    @Override
    protected void setListener() {
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
        mBanner.setImageLoader(new ProductImageLoader());
        mBanner.setImages(result.getImageUrls());
        mBanner.start();

        mTvPrice.setText("¥" + result.getPrice());
        mTvDesc.setText(result.getContent());
        mTvCate.setText(result.getName());
    }

    public class ProductImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            ImageUtils.loadImageUrl(imageView,((String)path));
        }
    }
}
