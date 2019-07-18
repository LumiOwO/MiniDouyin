package com.example.minidouyin.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.minidouyin.R;
import com.example.minidouyin.model.Video;

public class HistoryRecyclerAdapter extends BaseQuickAdapter<Video, BaseViewHolder>
{
	public HistoryRecyclerAdapter(int layoutResId)
	{
		super(layoutResId);
	}

	@Override
	protected void convert(BaseViewHolder helper, Video item)
	{
		helper.setText(R.id.history_No, helper.getAdapterPosition() + 1 + "");
		helper.setText(R.id.history_url, item.getVideoUrl());
	}
}
