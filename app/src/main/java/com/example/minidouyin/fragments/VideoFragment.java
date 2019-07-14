package com.example.minidouyin.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minidouyin.R;
import com.example.minidouyin.model.Video;
import com.example.minidouyin.net.IMiniDouyinService;
import com.example.minidouyin.net.NetManager;
import com.example.minidouyin.net.OnNetListener;
import com.example.minidouyin.net.response.GetVideosResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class VideoFragment extends Fragment {
	private static final String TAG = "VideoFragment";

	private NetManager mNetManager = new NetManager();

	private RecyclerView mRecyclerView;
	private List<Video> mVideoList = new ArrayList<>();

	private static class VideoViewHolder extends RecyclerView.ViewHolder {
		private VideoView mVideoView;
		private Button mBtnFollow;
		private Button mBtnLike;
		private Button mBtnComment;
		private Button mBtnShare;
//		private TextView mTVInfo;

		public VideoViewHolder(@NonNull View itemView) {
			super(itemView);
			mVideoView = itemView.findViewById(R.id.rv_video_container);
			mBtnFollow = itemView.findViewById(R.id.rv_video_btn_follow);
			mBtnLike = itemView.findViewById(R.id.rv_video_btn_like);
			mBtnComment = itemView.findViewById(R.id.rv_video_btn_comment);
			mBtnShare = itemView.findViewById(R.id.rv_video_btn_share);
		}

		public static VideoViewHolder create(Context context, ViewGroup parent) {
			View v = LayoutInflater.from(context).inflate(R.layout.layout_rv_video, parent, false);
			return new VideoViewHolder(v);
		}

		public void bind(Activity activity, Video video) {
			if (!video.getVideoUrl().isEmpty()) {
//				mVideoView.setMediaController(new MediaController(activity.getApplicationContext()));
				mVideoView.setVideoURI(Uri.parse(video.getVideoUrl()));
				mVideoView.requestFocus();
				mVideoView.start();
			}
			mBtnFollow.setText(video.getStudentId());
		}
	}


	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_video, container, false);

		mRecyclerView = view.findViewById(R.id.rv_video);

		mNetManager.setOnGetListener(new OnNetListener()
		{
			@Override
			public void exec(Response<?> response)
			{
				if (response.isSuccessful() && response.body() != null) {
					GetVideosResponse body = (GetVideosResponse)response.body();
					mVideoList = body.getVideos();
					mRecyclerView.getAdapter().notifyDataSetChanged();
				}
				Toast.makeText(getContext(), "refresh success", Toast.LENGTH_SHORT).show();
				Log.d(TAG, "initVideoList: " + mVideoList.size());
			}
		});

		initRecyclerView();
		initVideoList();
		return view;
	}

	private void initRecyclerView()
	{
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		mRecyclerView.setAdapter(new RecyclerView.Adapter<VideoViewHolder>() {
			@NonNull
			@Override
			public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
				return VideoViewHolder.create(getContext(), parent);
			}

			@Override
			public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
				Log.d(TAG, "onBindViewHolder: " + position);
				holder.bind(getActivity(), mVideoList.get(position));
			}

			@Override
			public int getItemCount() {
				return mVideoList.size();
			}
		});
		PagerSnapHelper snapHelper = new PagerSnapHelper();
		snapHelper.attachToRecyclerView(mRecyclerView);

	}

	private void initVideoList() {
		Toast.makeText(getContext(), "refresh begin", Toast.LENGTH_SHORT).show();
		mNetManager.execGetFeeds();
	}

}
