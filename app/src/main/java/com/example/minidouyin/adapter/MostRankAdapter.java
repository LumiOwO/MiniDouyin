package com.example.minidouyin.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.minidouyin.R;
import com.example.minidouyin.db.StudentVideoCountTuple;

public class MostRankAdapter extends BaseQuickAdapter<StudentVideoCountTuple, BaseViewHolder>
{
	public MostRankAdapter(int layoutResId)
	{
		super(layoutResId);
	}

	@Override
	protected void convert(BaseViewHolder helper, StudentVideoCountTuple item)
	{
		int rank = helper.getAdapterPosition() + 1;

		int red = Color.rgb(255, 0, 0);
		int orange = Color.rgb(255, 152, 0);
		int yellow = Color.rgb(255, 198, 109);
		int black = Color.BLACK;

		helper.setText(R.id.most_rank_studentID, "" + item.getStudentId());
		helper.setText(R.id.most_rank_num, "" + item.getVideoCount());

		int numColor = rank == 1? red:
						rank == 2? orange:
						rank == 3? yellow: black;
		helper.setTextColor(R.id.most_rank_num, numColor);

	}
}
