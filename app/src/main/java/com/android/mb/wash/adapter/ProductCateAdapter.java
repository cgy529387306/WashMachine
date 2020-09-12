package com.android.mb.wash.adapter;

import android.graphics.Color;

import com.android.mb.wash.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ProductCateAdapter extends BaseQuickAdapter<String, BaseViewHolder>{

	private int mSelectPos = 0;

	public ProductCateAdapter(List<String> data) {
		super(R.layout.item_product_cate, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, String string) {
		if(mSelectPos == helper.getAdapterPosition()){
			helper.setVisible(R.id.view_select,true);
			helper.convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
			helper.setTextColor(R.id.tv_cate, Color.parseColor("#004384"));
		} else {
            helper.setVisible(R.id.view_select,false);
			helper.convertView.setBackgroundColor(Color.parseColor("#f7f7f7"));
			helper.setTextColor(R.id.tv_cate, Color.parseColor("#666666"));
		}
		helper.setText(R.id.tv_cate,string);
	}


	public int getSelectPos() {
		return mSelectPos;
	}

	public void setSelectPos(int selectPos) {
		this.mSelectPos = selectPos;
		notifyDataSetChanged();
	}

}
