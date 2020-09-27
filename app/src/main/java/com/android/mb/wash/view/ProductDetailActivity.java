package com.android.mb.wash.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.wash.R;
import com.android.mb.wash.base.BaseMvpActivity;
import com.android.mb.wash.entity.Advert;
import com.android.mb.wash.entity.ProductBean;
import com.android.mb.wash.entity.VideoData;
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
    private ProductBean mProductBean;

    @Override
    protected ProductDetailPresenter createPresenter() {
        return new ProductDetailPresenter();
    }

    @Override
    protected void loadIntent() {
        mProductBean = (ProductBean) getIntent().getSerializableExtra("productBean");
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
        initDetail();
//        getDataFormServer();
    }

    @Override
    protected void setListener() {
    }

    private void getDataFormServer(){
        if (mProductBean != null){
            Map<String,Object> requestMap = new HashMap<>();
            requestMap.put("productId",mProductBean.getId());
            mPresenter.getDetail(requestMap);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
    }

    private void initDetail(){
        mBanner.setImageLoader(new ProductImageLoader());
        mBanner.setImages(mProductBean.getImageUrls());
        mBanner.start();

        mTvPrice.setText("¥" + mProductBean.getPrice());
        mTvDesc.setText(mProductBean.getContent());
        mTvCate.setText(mProductBean.getName());
    }


    @Override
    public void getDetail(VideoData result) {

    }

    public class ProductImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            ImageUtils.loadImageUrl(imageView,((String)path));
        }
    }
}
