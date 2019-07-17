package com.example.minidouyin.fragments.nearby;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.minidouyin.R;
import com.example.minidouyin.model.Video;
import com.example.minidouyin.net.NetManager;
import com.example.minidouyin.net.OnNetListener;
import com.example.minidouyin.net.response.GetVideosResponse;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

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
				List<GetVideosResponse.Feed> feeds = response.getFeeds();
				List<Video> list = new ArrayList<>();
				for(int i=0; i<feeds.size(); i++)
				{
					GetVideosResponse.Feed feed = feeds.get(i);
					Video video = new Video();

					video.setUsername(feed.getUser_name());
					video.setStudentId(feed.getStudent_id());
					video.setImageUrl(feed.getImage_url());
					video.setVideoUrl(feed.getVideo_url());

					list.add(video);
				}
				// update view
				notifyDataSetChanged();

				NearbyVideoAdapter.this.setNewData(list);
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
