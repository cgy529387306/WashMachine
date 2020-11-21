package com.android.mb.wash.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mb.wash.R;
import com.android.mb.wash.adapter.CommentAdapter;
import com.android.mb.wash.adapter.ImageAdapter;
import com.android.mb.wash.base.BaseMvpActivity;
import com.android.mb.wash.constants.ProjectConstants;
import com.android.mb.wash.entity.CommentListData;
import com.android.mb.wash.entity.CurrentUser;
import com.android.mb.wash.entity.PostBean;
import com.android.mb.wash.presenter.PostDetailPresenter;
import com.android.mb.wash.utils.AppHelper;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.ImageUtils;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.utils.ToastHelper;
import com.android.mb.wash.view.interfaces.IPostDetailView;
import com.android.mb.wash.widget.MyDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostDetailActivity extends BaseMvpActivity<PostDetailPresenter, IPostDetailView> implements IPostDetailView, View.OnClickListener, BaseQuickAdapter.OnItemClickListener, OnRefreshListener, OnLoadMoreListener {

    private EditText mEtContent;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private CommentAdapter mAdapter;
    private int mCurrentPage = 1;
    private PostBean mPostBean;

    private ImageView mIvAvatar;
    private TextView mTvName;
    private TextView mTvTime;
    private TextView mTvContent;
    private TextView mTvComment;
    private TextView mTvPraiseCount;
    private GridView mGridView;

    @Override
    protected PostDetailPresenter createPresenter() {
        return new PostDetailPresenter();
    }

    @Override
    protected void loadIntent() {
        mPostBean = (PostBean) getIntent().getSerializableExtra("postBean");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post_detail;
    }

    @Override
    protected void initTitle() {
        setTitleText("动态");
    }

    @Override
    protected void bindViews() {
        mEtContent = findViewById(R.id.et_content);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRefreshLayout.setEnableRefresh(false);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(LinearLayout.VERTICAL));
        mAdapter = new CommentAdapter(R.layout.item_comment, new ArrayList());
        mRecyclerView.setAdapter(mAdapter);

        //添加Header
        View header = LayoutInflater.from(this).inflate(R.layout.item_post_detail, mRecyclerView, false);
        mIvAvatar = header.findViewById(R.id.iv_avatar);
        mTvName = header.findViewById(R.id.tv_name);
        mTvTime = header.findViewById(R.id.tv_time);
        mTvContent = header.findViewById(R.id.tv_content);
        mTvComment = header.findViewById(R.id.tv_comment);
        mTvPraiseCount = header.findViewById(R.id.tv_praise_count);

        mGridView = header.findViewById(R.id.gridPic);
        mAdapter.addHeaderView(header);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        initDetail();
        onRefresh(null);
    }

    @Override
    protected void setListener() {
        findViewById(R.id.tv_confirm).setOnClickListener(this);
        mTvPraiseCount.setOnClickListener(this);
    }

    @Override
    public void getPostDetail(PostBean result) {

    }

    @Override
    public void comment(Object result) {
        mEtContent.setText("");
        AppHelper.hideSoftInputFromWindow(mEtContent);
        sendMsg(ProjectConstants.EVENT_UPDATE_POST,null);
        ToastHelper.showToast("评论成功");
        onRefresh(null);
    }

    @Override
    public void praise(Object result) {
        sendMsg(ProjectConstants.EVENT_UPDATE_POST,null);
        mPostBean.setPraised(!mPostBean.isPraised());
        mPostBean.setPraiseCount(mPostBean.isPraised()?mPostBean.getPraiseCount()+1:mPostBean.getPraiseCount()-1);
        initPraise();
    }

    @Override
    public void getPostComments(CommentListData result) {
        if (result!=null){
            if (result.isEnd()){
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            }
            if (mCurrentPage == 1){
                mTvComment.setVisibility(Helper.isNotEmpty(result.getList())?View.VISIBLE:View.GONE);
                mRefreshLayout.finishRefresh();
                mAdapter.setNewData(result.getList());
            }else{
                if (Helper.isEmpty(result)){
                    mRefreshLayout.finishLoadMoreWithNoMoreData();
                }else{
                    mAdapter.addData(result.getList());
                    mRefreshLayout.finishLoadMore();
                }
            }
        }
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
        getComments();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 1;
        getComments();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_praise_count){
            if (CurrentUser.getInstance().isLogin()){
                submitPraise();
            }else{
                NavigationHelper.startActivity(mContext, LoginActivity.class,null,false);
            }
        } else if (id == R.id.tv_confirm){
            if (CurrentUser.getInstance().isLogin()){
                String content = mEtContent.getText().toString().trim();
                if (Helper.isEmpty(content)){
                    ToastHelper.showToast("请输入评论内容");
                }else{
                    submitComment(content);
                }
            }else{
                NavigationHelper.startActivity(mContext, LoginActivity.class,null,false);
            }
        }
    }

    @Override
    protected void onDestroy() {
        AppHelper.hideSoftInputFromWindow(mEtContent);
        super.onDestroy();
    }

    private void initDetail(){
        initPraise();
        ImageUtils.displayAvatar(mIvAvatar,mPostBean.getUserAvatar());
        mTvName.setText(mPostBean.getNickName());
        mTvTime.setText(Helper.long2DateString(mPostBean.getCreateTime(),"yyyy-MM-dd HH:mm"));
        mTvContent.setText(mPostBean.getContent());
        mGridView.setAdapter(new ImageAdapter(mContext, mPostBean));
    }

    private void initPraise(){
        mTvPraiseCount.setText(mPostBean.getPraiseCount()+"");
        Drawable drawable= getResources().getDrawable(mPostBean.isPraised()?R.mipmap.icon_favor_s:R.mipmap.icon_favor_n);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mTvPraiseCount.setCompoundDrawables(drawable,null,null,null);
    }

    private void submitPraise(){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("dynamicId", mPostBean.getDynamicId());
        requestMap.put("type", mPostBean.isPraised()?"2":"1");
        mPresenter.praise(requestMap);
    }

    private void submitComment(String content){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("dynamicId", mPostBean.getDynamicId());
        requestMap.put("content", content);
        mPresenter.comment(requestMap);
    }
    private void getComments(){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("dynamicId", mPostBean.getDynamicId());
        requestMap.put("currentPage",mCurrentPage);
        requestMap.put("pageSize", ProjectConstants.PAGE_SIZE);
        mPresenter.getPostComments(requestMap);
    }

}
