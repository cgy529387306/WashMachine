package com.android.mb.wash.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.mb.wash.R;
import com.android.mb.wash.entity.CurrentUser;
import com.android.mb.wash.entity.HomeItem;
import com.android.mb.wash.entity.PostBean;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.view.LoginActivity;
import com.android.mb.wash.view.PostDetailActivity;
import com.android.mb.wash.view.PostListActivity;
import com.android.mb.wash.widget.MyDividerItemDecoration;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */
public class HomeAdapter extends BaseMultiItemQuickAdapter<HomeItem, BaseViewHolder> {

    public HomeAdapter(List<HomeItem> data) {
        super(data);
        addItemType(HomeItem.POST, R.layout.item_home_post);
        addItemType(HomeItem.PRODUCT, R.layout.item_home_product_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeItem item) {
        switch (helper.getItemViewType()){
            case HomeItem.POST:
                PostAdapter postAdapter = new PostAdapter(item.getPostBeanList());
                RecyclerView rvPost = helper.getView(R.id.rv_post);
                rvPost.setNestedScrollingEnabled(false);
                rvPost.setLayoutManager(new LinearLayoutManager(mContext));
                rvPost.setAdapter(postAdapter);
                rvPost.addItemDecoration(new MyDividerItemDecoration(LinearLayoutManager.VERTICAL));
                helper.setOnClickListener(R.id.tv_post_more, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CurrentUser.getInstance().isLogin()){
                            NavigationHelper.startActivity((Activity) mContext,PostListActivity.class,null,false);
                        }else{
                            NavigationHelper.startActivity((Activity) mContext, LoginActivity.class,null,false);
                        }
                    }
                });
                postAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        PostBean postBean = postAdapter.getItem(position);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("postBean",postBean);
                        NavigationHelper.startActivity((Activity) mContext, PostDetailActivity.class,bundle,false);
                    }
                });
                break;
            case HomeItem.PRODUCT:
                RecyclerView rvProduct = helper.getView(R.id.rv_product);
                rvProduct.setNestedScrollingEnabled(false);
                rvProduct.setLayoutManager(new LinearLayoutManager(mContext));
                rvProduct.setAdapter(new ProductTypeAdapter(item.getProductTypeList()));
                break;
        }
    }


}




