package com.example.minidouyin.adapter;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.minidouyin.R;
import com.example.minidouyin.model.Video;
import com.makeramen.roundedimageview.RoundedImageView;

public class HotRankAdapter extends BaseQuickAdapter<Video, BaseViewHolder>
{
	public HotRankAdapter(int layoutResId)
	{
		super(layoutResId);
	}

	@Override
	protected void convert(BaseViewHolder helper, Video item)
	{
		helper.setText(R.id.rank_No, helper.getAdapterPosition() + 1 + "");
		helper.setText(R.id.rank_url, item.getVideoUrl());
	}
}
