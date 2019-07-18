package com.example.minidouyin.adapter;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.minidouyin.R;
import com.example.minidouyin.db.StudentVideoCountTuple;
import com.example.minidouyin.model.Video;
import com.makeramen.roundedimageview.RoundedImageView;

public class MostRankAdapter extends BaseQuickAdapter<StudentVideoCountTuple, BaseViewHolder>
{
	public MostRankAdapter(int layoutResId)
	{
		super(layoutResId);
	}

	@Override
	protected void convert(BaseViewHolder helper, StudentVideoCountTuple item)
	{
		helper.setText(R.id.most_rank_username, "用户名：" + item.getStudentId());
		helper.setText(R.id.most_rank_num, "投稿数量：" + item.getVideoCount());
	}
}
