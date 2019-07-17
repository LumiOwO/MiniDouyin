package com.example.minidouyin.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minidouyin.R;
import com.example.minidouyin.adapter.VideoRecyclerAdapter;
import com.example.minidouyin.model.Video;
import com.example.minidouyin.net.NetManager;
import com.example.minidouyin.net.response.GetVideosResponse;
import com.example.minidouyin.utils.ScrollCalculatorHelper;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoFragment extends Fragment {
	private static final String TAG = "VideoFragment";

	private NetManager mNetManager = new NetManager();

	@BindView(R.id.rv_video)
	RecyclerView mRecyclerView;

	private VideoRecyclerAdapter mRecyclerAdapter;
	private List<Video> mVideoList = new ArrayList<>();
	private ScrollCalculatorHelper mScrollCalculatorHelper;

	private int mStartPosition = 0;


	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_video, container, false);

		ButterKnife.bind(this, view);

		mNetManager.setOnGetListener(response -> {
			if (response.isSuccessful() && response.body() != null) {
				GetVideosResponse body = (GetVideosResponse)response.body();
				mVideoList = body.getVideos();
				mRecyclerAdapter.setListData(mVideoList);
				mRecyclerAdapter.notifyDataSetChanged();
			}
			Toast.makeText(getContext(), "refresh success", Toast.LENGTH_SHORT).show();
			Log.d(TAG, "initVideoList: " + mVideoList.size());
		});

		initVideoList();
		initRecyclerView();

		Log.d("start", mStartPosition + "");
		mRecyclerView.getLayoutManager().scrollToPosition(mStartPosition);

		return view;
	}

	private void initRecyclerView()
	{
		final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
		mRecyclerView.setLayoutManager(linearLayoutManager);
		mRecyclerAdapter = new VideoRecyclerAdapter(getContext(), mVideoList);
		mRecyclerView.setAdapter(mRecyclerAdapter);
//		PagerSnapHelper snapHelper = new PagerSnapHelper();
//		snapHelper.attachToRecyclerView(mRecyclerView);

		//限定范围为屏幕一半的上下偏移180
		int playTop = CommonUtil.getScreenHeight(getContext()) / 2 - CommonUtil.dip2px(getContext(), 180);
		int playBottom = CommonUtil.getScreenHeight(getContext()) / 2 + CommonUtil.dip2px(getContext(), 180);
		//自定播放帮助类
		mScrollCalculatorHelper = new ScrollCalculatorHelper(R.id.rv_video_gsyPlayer, playTop, playBottom);

		mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

			int firstVisibleItem, lastVisibleItem;

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				mScrollCalculatorHelper.onScrollStateChanged(recyclerView, newState);
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
				lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
				Log.e(TAG, "onScrolled: " + firstVisibleItem + " " + lastVisibleItem);

				//这是滑动自动播放的代码
				mScrollCalculatorHelper.onScroll(recyclerView, firstVisibleItem, lastVisibleItem, lastVisibleItem - firstVisibleItem);
			}
		});
	}

	private void initVideoList() {
		// get bundle arguments
		Bundle bundle = getArguments();
		List<Video> playlist = (List<Video>)bundle.getSerializable("playlist");
		if(playlist == null) {
			Toast.makeText(getContext(), "refresh begin", Toast.LENGTH_SHORT).show();
			mNetManager.execGetFeeds();
		} else {
			mVideoList = playlist;
			mStartPosition = bundle.getInt("startPosition");
		}
	}

	public static VideoFragment launch()
	{
		return launch(null, -1);
	}

	public static VideoFragment launch(List<Video> playList, int startPosition)
	{
		Bundle bundle = new Bundle();
		bundle.putSerializable("playlist", (Serializable) playList);
		bundle.putInt("startPosition", startPosition);

		VideoFragment fragment = new VideoFragment();
		fragment.setArguments(bundle);

		return fragment;
	}

	@Override
	public void onPause() {
		super.onPause();
		GSYVideoManager.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		GSYVideoManager.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		GSYVideoManager.releaseAllVideos();
	}
}
