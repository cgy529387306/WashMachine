package com.android.mb.wash.adapter;

import android.graphics.Color;

import com.android.mb.wash.R;
import com.android.mb.wash.entity.Category;
import com.android.mb.wash.utils.Helper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ProductCateAdapter extends BaseQuickAdapter<Category, BaseViewHolder>{

	private int mSelectPos = 0;

	public ProductCateAdapter(List<Category> data) {
		super(R.layout.item_product_cate, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, Category category) {
		if(mSelectPos == helper.getAdapterPosition()){
			helper.setVisible(R.id.view_select,true);
			helper.convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
			helper.setTextColor(R.id.tv_cate, Color.parseColor("#004384"));
		} else {
            helper.setVisible(R.id.view_select,false);
			helper.convertView.setBackgroundColor(Color.parseColor("#f7f7f7"));
			helper.setTextColor(R.id.tv_cate, Color.parseColor("#666666"));
		}
		helper.setText(R.id.tv_cate,category.getCateName());
	}


	public int getSelectPos() {
		return mSelectPos;
	}

	public void setSelectPos(int selectPos) {
		this.mSelectPos = selectPos;
		notifyDataSetChanged();
	}

	public Category getSelectCategory() {
		return Helper.isEmpty(mData) ? null : mData.get(mSelectPos);
	}

}
