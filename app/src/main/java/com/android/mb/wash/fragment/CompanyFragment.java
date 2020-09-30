package com.android.mb.wash.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.mb.wash.R;
import com.android.mb.wash.adapter.CompanyAdapter;
import com.android.mb.wash.base.BaseFragment;
import com.android.mb.wash.utils.AppHelper;
import com.android.mb.wash.widget.MyDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by cgy on 16/7/18.
 */
public class CompanyFragment extends BaseFragment implements View.OnClickListener,BaseQuickAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private CompanyAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return  R.layout.frg_company;
    }

    @Override
    protected void bindViews(View view) {
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(LinearLayoutManager.VERTICAL, Color.parseColor("#F7F7F7"), AppHelper.calDpi2px(10)));
        mAdapter = new CompanyAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setEnableLoadMore(false);
    }

    @Override
    protected void processLogic() {
        Integer[] resArray = {R.mipmap.icon_company1,R.mipmap.icon_company2,R.mipmap.icon_company3,R.mipmap.icon_company4,R.mipmap.icon_company5};
        List<Integer> dataList = new ArrayList<>();
        for (int i=0; i<resArray.length; i++){
            dataList.add(resArray[i]);
        }
        mAdapter.setNewData(dataList);
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
    }




    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMoreWithNoMoreData();
    }


//
//    private void getListFormServer(){
//        Map<String,Object> requestMap = new HashMap<>();
//        requestMap.put("type",1);
//        mPresenter.getList(requestMap);
//    }
}
