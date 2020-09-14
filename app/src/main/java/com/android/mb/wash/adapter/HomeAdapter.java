package com.android.mb.wash.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;

import com.android.mb.wash.R;
import com.android.mb.wash.entity.HomeItem;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.utils.TestHelper;
import com.android.mb.wash.view.PostListActivity;
import com.android.mb.wash.view.ProductHotListActivity;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.android.mb.wash.constants.ProjectConstants.TEST_IMAGE;


/**
 * Created by necer on 2017/6/7.
 */
public class HomeAdapter extends BaseMultiItemQuickAdapter<HomeItem, BaseViewHolder> {

    public HomeAdapter(List<HomeItem> data) {
        super(data);
        addItemType(HomeItem.POST, R.layout.item_home_post);
        addItemType(HomeItem.NEW, R.layout.item_home_product);
        addItemType(HomeItem.HOT, R.layout.item_home_product);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeItem item) {
        switch (helper.getItemViewType()){
            case HomeItem.POST:
                RecyclerView rvPost = helper.getView(R.id.rv_post);
                rvPost.setNestedScrollingEnabled(false);
                rvPost.setLayoutManager(new LinearLayoutManager(mContext));
                rvPost.setAdapter(new PostAdapter(TestHelper.getTestImage()));

                helper.setOnClickListener(R.id.tv_post_more, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NavigationHelper.startActivity((Activity) mContext,PostListActivity.class,null,false);
                    }
                });
                break;
            case HomeItem.NEW:
            case HomeItem.HOT:
                GridView gridProduct = helper.getView(R.id.gridProduct);
                gridProduct.setAdapter(new ProductHotAdapter(mContext,TestHelper.getTestImage()));
                helper.setOnClickListener(R.id.tv_more_hot, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NavigationHelper.startActivity((Activity) mContext, ProductHotListActivity.class,null,false);
                    }
                });
                break;
        }
    }


}




