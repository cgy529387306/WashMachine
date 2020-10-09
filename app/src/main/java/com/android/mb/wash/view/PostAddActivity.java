package com.android.mb.wash.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.android.mb.wash.R;
import com.android.mb.wash.adapter.GridImageAdapter;
import com.android.mb.wash.base.BaseMvpActivity;
import com.android.mb.wash.constants.ProjectConstants;
import com.android.mb.wash.presenter.PublishPresenter;
import com.android.mb.wash.retrofit.http.util.RequestBodyUtil;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.ToastHelper;
import com.android.mb.wash.view.interfaces.IPublishView;
import com.android.mb.wash.widget.FullyGridLayoutManager;
import com.android.mb.wash.widget.MyDividerItemDecoration;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * Created by cgy on 2018\8\20 0020.
 */

public class PostAddActivity extends BaseMvpActivity<PublishPresenter,
        IPublishView> implements IPublishView,View.OnClickListener {

    private RecyclerView mRecyclerView;
    private GridImageAdapter mImageAdapter;
    private List<LocalMedia> mSelectImageList = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private EditText mEtContent;
    @Override
    protected void loadIntent() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post_add;
    }

    @Override
    protected void initTitle() {
        hideActionbar();
    }


    @Override
    protected void bindViews() {
        mEtContent = findViewById(R.id.et_content);
        mRecyclerView = findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mImageAdapter);
        initRecycleView();
    }

    private void initRecycleView(){
        FullyGridLayoutManager gridLayoutManager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mImageAdapter = new GridImageAdapter(this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                //拍照
                PictureSelector.create(PostAddActivity.this)
                        .openGallery(PictureMimeType.ofImage())
                        .previewImage(true)// 是否可预览图片 true or false
                        .isCamera(true)// 是否显示拍照按钮 true or false
                        .compress(true)// 是否压缩 true or false
                        .minimumCompressSize(500)// 小于100kb的图片不压缩
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        });
        mImageAdapter.setList(mSelectImageList);
        mImageAdapter.setSelectMax(9);
        mRecyclerView.setAdapter(mImageAdapter);
        mImageAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (mSelectImageList.size() > 0 && mSelectImageList.size()>position) {
                    LocalMedia media = mSelectImageList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    if (mediaType == 1){
                        PictureSelector.create(PostAddActivity.this).externalPicturePreview(position, mSelectImageList);
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
                mSelectImageList.addAll(images);
                mImageAdapter.setList(mSelectImageList);
            }
        }
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
    }



    @Override
    protected void setListener() {
        findViewById(R.id.iv_close).setOnClickListener(this);
        findViewById(R.id.tv_post).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_close){
            finish();
        } else if (id == R.id.tv_post){
            doPublish();
        }
    }

    private void doPublish(){
        String content = mEtContent.getText().toString().trim();
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("content",content);
        List<File> fileList = new ArrayList<>();
        if (Helper.isNotEmpty(mSelectImageList)){
            for (int i=0; i<mSelectImageList.size(); i++) {
                LocalMedia localMedia = mSelectImageList.get(i);
                fileList.add(new File(localMedia.getCompressPath()));
            }
        }
        mPresenter.publishDynamic(fileList,requestMap);
    }


    @Override
    protected PublishPresenter createPresenter() {
        return new PublishPresenter();
    }


    @Override
    public void publishSuccess(Object result) {
        sendMsg(ProjectConstants.EVENT_UPDATE_POST,null);
        ToastHelper.showLongToast("发布成功");
        finish();
    }
}
