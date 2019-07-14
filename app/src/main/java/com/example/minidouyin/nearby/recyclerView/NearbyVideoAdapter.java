package com.example.minidouyin.nearby.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minidouyin.R;
import com.example.minidouyin.model.GetVideosResponse;
import com.example.minidouyin.model.Video;
import com.example.minidouyin.service.IMiniDouyinService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NearbyVideoAdapter extends RecyclerView.Adapter
{
	// network
	private Retrofit mRetrofit;
	private IMiniDouyinService mService;

	// content
	private ArrayList<Video> mList;

	public NearbyVideoAdapter()
	{
		mRetrofit = new Retrofit.Builder()
				.baseUrl(IMiniDouyinService.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		mService = mRetrofit.create(IMiniDouyinService.class);

		mList = new ArrayList<Video>();
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.videopreview_nearby, parent, false);

		return new NearbyVideoPreviewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
	{
		NearbyVideoPreviewHolder videoHolder = (NearbyVideoPreviewHolder)holder;

		videoHolder.setImage(mList.get(position).getImageUrl());
	}

	@Override
	public int getItemCount()
	{
		return mList.size();
	}

	public void refreshView()
	{
		Call<GetVideosResponse> call = mService.getVideo();
		call.enqueue(new Callback<GetVideosResponse>()
		{
			@Override
			public void onResponse(Call<GetVideosResponse> call, Response<GetVideosResponse> response)
			{
				// create videos
				List<GetVideosResponse.Feed> feeds = response.body().getFeeds();
				mList.clear();
				for(int i=0; i<feeds.size(); i++)
				{
					GetVideosResponse.Feed feed = feeds.get(i);
					Video video = new Video();

					video.setUserName(feed.getUser_name());
					video.setStudentId(feed.getStudent_id());
					video.setImageUrl(feed.getImage_url());
					video.setVideoUrl(feed.getVideo_url());

					mList.add(video);
				}
				// update view
				notifyDataSetChanged();
			}

			@Override
			public void onFailure(Call<GetVideosResponse> call, Throwable t)
			{
				t.printStackTrace();
			}
		});
	}
}
