package com.example.minidouyin.adapter;

import android.graphics.Color;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.minidouyin.R;
import com.example.minidouyin.db.VideoRecord;
import com.example.minidouyin.model.Video;
import com.google.android.exoplayer2.C;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HotRankAdapter extends BaseQuickAdapter<Video, BaseViewHolder>
{
	public HotRankAdapter(int layoutResId)
	{
		super(layoutResId);
	}

	private List<Integer> mHotValues = new ArrayList<>();

	@Override
	protected void convert(BaseViewHolder helper, Video item)
	{
		int rank = helper.getAdapterPosition() + 1;

		int red = Color.rgb(255, 0, 0);
		int orange = Color.rgb(255, 152, 0);
		int yellow = Color.rgb(255, 198, 109);

		int hotValueColor = rank <= 3? red: orange;
		int rankColor = rank == 1? red:
				rank < 4? orange: yellow;

		// set rank color
		helper.setText(R.id.rank_No, helper.getAdapterPosition() + 1 + "");
		helper.setTextColor(R.id.rank_No, rankColor);
		// set url text
		helper.setText(R.id.rank_url, item.getVideoUrl());
		// set hot value
		int hotValue = mHotValues.get(rank-1);
		helper.setText(R.id.rank_hotValue, String.format(Locale.getDefault(), "%.1f", hotValue / 10.0f));
		helper.setTextColor(R.id.rank_hotValue, hotValueColor);
	}

	public void updateHotValue(List<VideoRecord> videoRecords)
	{
		mHotValues.clear();
		for(VideoRecord record : videoRecords)
			mHotValues.add(record.getHotValue());
	}
}
