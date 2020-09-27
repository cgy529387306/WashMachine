package com.android.mb.wash.entity;

import java.io.Serializable;

/**
 * 分类
 *
 */
public class Category implements Serializable{

	private String cateId;
	private String cateName;
	private String createTime;
	private String updateTime;

	public String getCateId() {
		return cateId == null ? "" : cateId;
	}

	public String getCateName() {
		return cateName == null ? "" : cateName;
	}

	public String getCreateTime() {
		return createTime == null ? "" : createTime;
	}

	public String getUpdateTime() {
		return updateTime == null ? "" : updateTime;
	}


}
