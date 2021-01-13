package com.android.mb.wash.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.android.mb.wash.R;
import com.android.mb.wash.entity.PostBean;
import com.android.mb.wash.service.ScheduleMethods;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.HttpManager;
import com.android.mb.wash.utils.ImageUtils;
import com.android.mb.wash.utils.ToastHelper;
import com.android.mb.wash.widget.NestedGridView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;


/**
 * Created by necer on 2017/6/7.
 */
public class PostAdapter extends BaseQuickAdapter<PostBean, BaseViewHolder> {

    private Drawable mPraiseDrawable;
    private Drawable mUnPraiseDrawable;

    public PostAdapter(List data, Context context) {
        super(R.layout.item_post, data);
        mPraiseDrawable = context.getResources().getDrawable(R.mipmap.icon_favor_s);
        mPraiseDrawable.setBounds(0, 0, mPraiseDrawable.getMinimumWidth(), mPraiseDrawable.getMinimumHeight());
        mUnPraiseDrawable = context.getResources().getDrawable(R.mipmap.icon_favor_n);
        mUnPraiseDrawable.setBounds(0, 0, mUnPraiseDrawable.getMinimumWidth(), mUnPraiseDrawable.getMinimumHeight());
    }

    @Override
    protected void convert(BaseViewHolder helper, PostBean item) {
        ImageUtils.displayAvatar(helper.getView(R.id.iv_avatar),item.getUserAvatar());
        helper.setText(R.id.tv_name,item.getNickName());
        helper.setText(R.id.tv_content,item.getContent());
        helper.setText(R.id.tv_time, Helper.long2DateString(item.getCreateTime(),"yyyy-MM-dd HH:mm"));
        helper.setText(R.id.tv_comment_count,item.getCommentCount()+"");

        NestedGridView gridView = (NestedGridView) helper.getView(R.id.gridPic);
        gridView.setAdapter(new ImageAdapter(mContext, item));
        gridView.setOnTouchInvalidPositionListener(new NestedGridView.OnTouchInvalidPositionListener() {
            @Override
            public boolean onTouchInvalidPosition(int motionEvent) {
                return false; //不终止路由事件让父级控件处理事件
            }
        });

        TextView tvPraiseCount = helper.getView(R.id.tv_praise_count);
        tvPraiseCount.setCompoundDrawables(item.isPraised()?mPraiseDrawable:mUnPraiseDrawable,null,null,null);
        tvPraiseCount.setText(item.getPraiseCount()+"");

        tvPraiseCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> requestMap = new HashMap<>();
                requestMap.put("dynamicId", item.getDynamicId());
                requestMap.put("type", item.isPraised()?"2":"1"); //1：点赞  2：取消点赞
                Observable observable = ScheduleMethods.getInstance().praise(requestMap);
                Subscriber<Object> subscriber = new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(!TextUtils.isEmpty(e.getMessage())){
                           ToastHelper.showLongToast(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(Object result) {
                        if (item.isPraised()) {
                            // 取消点赞成功
                            item.setPraised(false);
                            item.setPraiseCount(item.getPraiseCount()-1);
                        } else {
                            // 点赞成功
                            item.setPraised(true);
                            item.setPraiseCount(item.getPraiseCount()+1);
                        }
                        tvPraiseCount.setCompoundDrawables(item.isPraised()?mPraiseDrawable:mUnPraiseDrawable,null,null,null);
                        tvPraiseCount.setText(item.getPraiseCount()+"");
                    }
                };
                HttpManager.instance().toSubscribe(observable, subscriber);
            }
        });
    }


}




