package com.android.mb.wash.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.mb.wash.R;
import com.android.mb.wash.base.BaseMvpActivity;
import com.android.mb.wash.constants.ProjectConstants;
import com.android.mb.wash.entity.Avatar;
import com.android.mb.wash.entity.CurrentUser;
import com.android.mb.wash.entity.UserBean;
import com.android.mb.wash.presenter.AccountPresenter;
import com.android.mb.wash.utils.Base64Util;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.ImageUtils;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.view.interfaces.IAccountView;
import com.android.mb.wash.widget.CircleImageView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cgy on 2018\8\20 0020.
 */

public class PersonInfoActivity extends BaseMvpActivity<AccountPresenter,
        IAccountView> implements IAccountView,View.OnClickListener {

    private TextView mTvName;
    private CircleImageView mIvAvatar;

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
        mIvAvatar = findViewById(R.id.iv_avatar);
        mTvName = findViewById(R.id.tv_name);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        initUserInfo();
    }



    @Override
    protected void setListener() {
        findViewById(R.id.rl_avatar).setOnClickListener(this);
        findViewById(R.id.rl_nickname).setOnClickListener(this);
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
        } else if (id == R.id.rl_nickname){
            Bundle bundle = new Bundle();
            bundle.putString("title","昵称");
            bundle.putString("value",CurrentUser.getInstance().getNickname());
            bundle.putString("hint","请输入昵称");
            bundle.putString("emptyHint","请输入昵称");
            NavigationHelper.startActivityForResult(mContext,EditActivity.class,bundle,22);
        }
    }


    @Override
    protected AccountPresenter createPresenter() {
        return new AccountPresenter();
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
            } else if (requestCode == 22){
                String editText = data.getStringExtra("editText");
                if (Helper.isNotEmpty(editText)){
                    mTvName.setText(editText);
                    Map<String,Object> requestMap = new HashMap<>();
                    requestMap.put("nickname",editText);
                    requestMap.put("userid",CurrentUser.getInstance().getUserid());
                    mPresenter.updateInfo(requestMap);
                }
            }
        }
    }

    private void initUserInfo(){
        if (CurrentUser.getInstance().isLogin()){
            mTvName.setText(CurrentUser.getInstance().getNickname());
            ImageUtils.displayAvatar(mIvAvatar,CurrentUser.getInstance().getAvatar_url());
        }
    }

    private void doUploadAvatar(LocalMedia localMedia){
        File avatarFile = new File(localMedia.getCompressPath());
        mPresenter.uploadAvatar(avatarFile);
    }


    @Override
    public void getSuccess(UserBean result) {

    }

    @Override
    public void updateInfo(UserBean result) {
        sendMsg(ProjectConstants.EVENT_UPDATE_USER_INFO,null);
    }

    @Override
    public void uploadAvatar(Avatar result) {
        sendMsg(ProjectConstants.EVENT_UPDATE_USER_INFO,null);
        if (result!=null){
            ImageUtils.displayAvatar(mIvAvatar,result.getAvatar_url());
        }
    }
}
