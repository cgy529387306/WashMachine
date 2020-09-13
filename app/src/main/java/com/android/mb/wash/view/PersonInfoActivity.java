package com.android.mb.wash.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.mb.wash.R;
import com.android.mb.wash.base.BaseMvpActivity;
import com.android.mb.wash.entity.VideoListData;
import com.android.mb.wash.presenter.SearchPresenter;
import com.android.mb.wash.utils.Base64Util;
import com.android.mb.wash.view.interfaces.ISearchView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * Created by cgy on 2018\8\20 0020.
 */

public class PersonInfoActivity extends BaseMvpActivity<SearchPresenter,
        ISearchView> implements ISearchView,View.OnClickListener {

    @Override
    protected void loadIntent() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_info;
    }

    @Override
    protected void initTitle() {
        setTitleText("资料详情");
    }


    @Override
    protected void bindViews() {
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
    }



    @Override
    protected void setListener() {
        findViewById(R.id.rl_avatar).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rl_avatar){
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                    .previewImage(true)// 是否可预览图片 true or false
                    .isCamera(true)// 是否显示拍照按钮 true or false
                    .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    .enableCrop(true)// 是否裁剪 true or false
                    .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                    .compress(true)// 是否压缩 true or false
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    .forResult(11);
        }
    }


    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter();
    }

    @Override
    public void getSuccess(VideoListData result) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 11) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList.size() > 0) {
                    LocalMedia localMedia = selectList.get(0);
                    doUploadAvatar(localMedia);
                }
            }
        }
    }

    private void doUploadAvatar(LocalMedia localMedia){
        showLoading();
        String avatarBase64 = Base64Util.getBase64ImageStr(localMedia.getCompressPath());
        Log.d("#######avatar#########",avatarBase64);
    }


}
