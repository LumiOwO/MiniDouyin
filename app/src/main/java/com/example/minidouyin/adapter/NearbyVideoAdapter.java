package com.example.minidouyin.adapter;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.minidouyin.R;
import com.example.minidouyin.model.Video;
import com.example.minidouyin.net.NetManager;
import com.example.minidouyin.net.OnNetListener;
import com.example.minidouyin.net.response.GetVideosResponse;
import com.makeramen.roundedimageview.RoundedImageView;

import retrofit2.Response;

public class NearbyVideoAdapter extends BaseQuickAdapter<Video, BaseViewHolder>
{
	// content
	private NetManager mNetManager = new NetManager();

	public NearbyVideoAdapter(int layoutResId)
	{
		super(layoutResId);

		mNetManager.setOnGetListener(new OnNetListener()
		{
			@Override
			public void exec(Response<?> res)
			{
				// create videos
				GetVideosResponse response = (GetVideosResponse)res.body();
				NearbyVideoAdapter.this.setNewData(response.getVideos());
			}
		});
	}

	@Override
	protected void convert(BaseViewHolder helper, Video item)
	{
		Glide.with(mContext)
				.load(item.getImageUrl())
				.into((RoundedImageView) helper.getView(R.id.preview_nearby));
	}

	public void refreshView()
	{
		mNetManager.execGetFeeds();
	}

}
