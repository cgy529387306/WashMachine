package com.android.mb.wash.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.android.mb.wash.R;
import com.android.mb.wash.entity.Tag;
import com.android.mb.wash.entity.Video;
import com.android.mb.wash.utils.ImageUtils;
import com.android.mb.wash.widget.taglayout.FlowTagLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
public class MovieListAdapter extends BaseQuickAdapter<Video, BaseViewHolder> {

    private boolean mIsCanEdit = false;

    public MovieListAdapter(int layoutResId, @Nullable List<Video> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Video item) {
        helper.setText(R.id.tv_title,item.getName());
        helper.setText(R.id.tv_play_count,String.format(mContext.getString(R.string.play_times_pre), item.getPlayCount()));
        CheckBox checkBox = helper.getView(R.id.checkBox);
        checkBox.setVisibility(mIsCanEdit? View.VISIBLE:View.GONE);
        ImageUtils.loadImageUrlLight(helper.getView(R.id.iv_cover), item.getCoverUrl1());
        FlowTagLayout flowTagLayout = helper.getView(R.id.tagLayout);
        TagSmallAdapter<Tag> tagAdapter = new TagSmallAdapter<>(mContext);
        flowTagLayout.setAdapter(tagAdapter);
        flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_NONE);
        tagAdapter.clearAndAddAll(item.getTagList());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setSelect(isChecked);
            }
        });

    }

    public void setCanEdit(boolean canEdit) {
        mIsCanEdit = canEdit;
        notifyDataSetChanged();
    }

}
