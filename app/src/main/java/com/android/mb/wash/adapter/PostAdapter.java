package com.android.mb.wash.adapter;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.android.mb.wash.R;
import com.android.mb.wash.entity.PostBean;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.ImageUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */
public class PostAdapter extends BaseQuickAdapter<PostBean, BaseViewHolder> {


    public PostAdapter(List data) {
        super(R.layout.item_post, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PostBean item) {
        ImageUtils.displayAvatar(helper.getView(R.id.iv_avatar),item.getUserAvatar());
        helper.setText(R.id.tv_name,item.getNickName());
        helper.setText(R.id.tv_content,item.getContent());
        helper.setText(R.id.tv_time, Helper.long2DateString(item.getCreateTime(),"yyyy-MM-dd HH:mm"));
        helper.setText(R.id.tv_comment_count,item.getCommentCount()+"");
        helper.setText(R.id.tv_praise_count,item.getPraiseCount()+"");

        TextView tvPraiseCount = helper.getView(R.id.tv_praise_count);
        Drawable drawable = mContext.getResources().getDrawable(item.isPraised()?R.mipmap.icon_favor_s:R.mipmap.icon_favor_n);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvPraiseCount.setCompoundDrawables(drawable,null,null,null);
    }


}




