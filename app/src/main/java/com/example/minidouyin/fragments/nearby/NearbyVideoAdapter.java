package com.example.minidouyin.fragments.nearby;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minidouyin.R;
import com.example.minidouyin.model.Video;
import com.example.minidouyin.net.NetManager;
import com.example.minidouyin.net.OnNetListener;
import com.example.minidouyin.net.response.GetVideosResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class NearbyVideoAdapter extends RecyclerView.Adapter
{
	// content
	private List<Video> mList = new ArrayList<>();
	private NetManager mNetManager = new NetManager();

	public NearbyVideoAdapter()
	{
		mNetManager.setOnGetListener(new OnNetListener()
		{
			@Override
			public void exec(Response<?> res)
			{
				// create videos
				GetVideosResponse response = (GetVideosResponse)res.body();
				mList = response.getVideos();
				// update view
				notifyDataSetChanged();
			}
		});
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.videopreview_nearby, parent, false);

		return new NearbyVideoPreviewHolder(view, mList);
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
		mNetManager.execGetFeeds();
	}

}
