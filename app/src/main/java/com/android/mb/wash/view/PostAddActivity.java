package com.android.mb.wash.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.ImageUtils;
import com.android.mb.wash.utils.ToastHelper;
import com.android.mb.wash.view.interfaces.IPublishView;
import com.android.mb.wash.widget.BottomMenuDialog;
import com.android.mb.wash.widget.FullyGridLayoutManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private int mType = 0;//0未选择 1图片 2视频
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

    private BottomMenuDialog mPickDialog;
    private void showPickDialog() {
        if (mPickDialog == null) {
            mPickDialog = new BottomMenuDialog.Builder(mContext)
                    .addMenu("图片", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pickPhotoOrVideo(1);
                        }
                    }).addMenu("视频", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pickPhotoOrVideo(2);
                        }
                    }).create();
        }
        mPickDialog.show();
    }

    private void pickPhotoOrVideo(int type){
        if (mType != type) {
            if (type == 2) {
                // 如果原来是选择照片后面改成选择视频需要将图片清空
                mSelectImageList.clear();
            }
            mType = type;
        }
        mPickDialog.dismiss();
        mImageAdapter.setSelectMax(mType==2?1:9);
        int maxNum = type == 1 ? 9-mSelectImageList.size():1;
        int mimeType = type == 1 ? PictureMimeType.ofImage():PictureMimeType.ofVideo();
        int requestCode = type == 1 ? PictureConfig.CHOOSE_REQUEST:PictureConfig.REQUEST_CAMERA;
        PictureSelector.create(PostAddActivity.this)
                .openGallery(mimeType)
                .maxSelectNum(maxNum)
                .videoMaxSecond(30)
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .compress(true)// 是否压缩 true or false
                .minimumCompressSize(500)// 小于100kb的图片不压缩
                .forResult(requestCode);
    }

    private void initRecycleView(){
        FullyGridLayoutManager gridLayoutManager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mImageAdapter = new GridImageAdapter(this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                showPickDialog();
            }
        });
        mImageAdapter.setList(mSelectImageList);
        mRecyclerView.setAdapter(mImageAdapter);
        mImageAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (mSelectImageList.size() > 0 && mSelectImageList.size()>position) {
                    LocalMedia media = mSelectImageList.get(position);
                    int mimeType = media.getMimeType();
                    if (mimeType == PictureMimeType.ofImage()) {
                        PictureSelector.create(PostAddActivity.this).externalPicturePreview(position, mSelectImageList);
                    } else if (mimeType == PictureMimeType.ofVideo()) {
                        PictureSelector.create(PostAddActivity.this).externalPictureVideo(media.getPath());
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
            } else if (requestCode == PictureConfig.REQUEST_CAMERA) {
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
        showProgressDialog();
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("content",content);
        List<File> fileList = new ArrayList<>();
        File videoFile = null;
        if (mType == 1) {
            if (Helper.isNotEmpty(mSelectImageList)){
                for (int i=0; i<mSelectImageList.size(); i++) {
                    LocalMedia localMedia = mSelectImageList.get(i);
                    fileList.add(new File(localMedia.getCompressPath()));
                }
            }
        } else if (mType == 2){
            if (Helper.isNotEmpty(mSelectImageList)){
                LocalMedia localMedia = mSelectImageList.get(0);
                Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(localMedia.getPath(), MediaStore.Images.Thumbnails.MINI_KIND);
                String filePath = ImageUtils.saveBitMapToFile(mContext,"thumbnail.jpg",bitmap);
                fileList.add(new File(filePath));
                videoFile = new File(localMedia.getPath());
            }

        }
        mPresenter.publishDynamic(fileList,videoFile,requestMap);
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
