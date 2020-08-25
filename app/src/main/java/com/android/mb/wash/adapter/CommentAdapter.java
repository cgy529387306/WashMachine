package com.android.mb.wash.adapter;

import android.support.annotation.Nullable;

import com.android.mb.wash.R;
import com.android.mb.wash.entity.Comment;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.ImageUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
public class CommentAdapter extends BaseQuickAdapter<Comment, BaseViewHolder> {

    public CommentAdapter(int layoutResId, @Nullable List<Comment> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Comment item) {
        helper.setText(R.id.tv_name,item.getUserNickName());
        helper.setText(R.id.tv_time, Helper.long2DateString(item.getCreateTime()));
        helper.setText(R.id.tv_content,item.getContent());
        ImageUtils.displayAvatar(helper.getView(R.id.iv_avatar),item.getUserAvatarUrl());
    }
}
