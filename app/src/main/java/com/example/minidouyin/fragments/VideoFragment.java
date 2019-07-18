package com.example.minidouyin.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minidouyin.R;
import com.example.minidouyin.adapter.VideoRecyclerAdapter;
import com.example.minidouyin.db.HistoryRecord;
import com.example.minidouyin.db.MiniDouYinDatabaseHelper;
import com.example.minidouyin.model.CurrentUser;
import com.example.minidouyin.model.Video;
import com.example.minidouyin.net.NetManager;
import com.example.minidouyin.net.response.GetVideosResponse;
import com.example.minidouyin.utils.ScrollCalculatorHelper;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoFragment extends Fragment {
	private static final String TAG = "VideoFragment";

	private NetManager mNetManager = new NetManager();
	private boolean canFresh = true;

	@BindView(R.id.refresh_layout)
	SmartRefreshLayout mRefreshLayout;

	@BindView(R.id.rv_video)
	RecyclerView mRecyclerView;

	private VideoRecyclerAdapter mRecyclerAdapter;
	private List<Video> mVideoList = new ArrayList<>();
	private ScrollCalculatorHelper mScrollCalculatorHelper;

	private int mStartPosition = 0;

	private Handler mHandler = new Handler();

	// database
	private MiniDouYinDatabaseHelper mMiniDouYinDatabaseHelper = new MiniDouYinDatabaseHelper(getContext());
	private MiniDouYinDatabaseHelper.InsertVideoRecordsTask mInsertVideoRecordsTask;
	private MiniDouYinDatabaseHelper.InsertHistoryRecordTask mInsertHistoryRecordTask;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_video, container, false);

		ButterKnife.bind(this, view);

		initVideoList();
		initRecyclerView();

		mNetManager.setOnGetListener(response -> {
			if (response.isSuccessful() && response.body() != null) {
				GetVideosResponse body = (GetVideosResponse)response.body();
				mVideoList = body.getVideos();
				mRecyclerAdapter.setListData(mVideoList);
				mInsertVideoRecordsTask = mMiniDouYinDatabaseHelper.executeInsertVideoRecords(mVideoList);
				Log.e(TAG, "onCreateView: " + mVideoList.size());
				mRecyclerAdapter.notifyDataSetChanged();
			}
			Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
			mRefreshLayout.finishRefresh();
			playFirstVideo();
//			Log.d(TAG, "initVideoList: " + mVideoList.size());
		});

		if(canFresh) {
			mRefreshLayout.setOnRefreshListener(new OnRefreshListener()
			{
				@Override
				public void onRefresh(@NonNull RefreshLayout refreshLayout)
				{
					mNetManager.execGetFeeds();
				}
			});
			mRefreshLayout.setRefreshHeader(new MaterialHeader(getContext()));
		} else {
			mRefreshLayout.setEnableRefresh(false);
		}


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
		PagerSnapHelper snapHelper = new PagerSnapHelper();
		snapHelper.attachToRecyclerView(mRecyclerView);

		int playTop = 0;
		int playBottom = CommonUtil.getScreenHeight(getContext());
		//自定播放帮助类
		mScrollCalculatorHelper = new ScrollCalculatorHelper(R.id.rv_video_gsyPlayer, playTop, playBottom);

		mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

			int firstVisibleItem, lastVisibleItem;

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				mScrollCalculatorHelper.onScrollStateChanged(recyclerView, newState);

				firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				HistoryRecord historyRecord = new HistoryRecord(CurrentUser.getStudentID(), mVideoList.get(firstVisibleItem).getId(), sdf.format(new Date()));
				mInsertHistoryRecordTask = mMiniDouYinDatabaseHelper.executeInsertHistoryRecord(historyRecord);

				Log.d("onScrollStateChanged", "scroll");
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
				lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
				mScrollCalculatorHelper.onScroll(recyclerView, firstVisibleItem, lastVisibleItem, lastVisibleItem - firstVisibleItem + 1);
			}
		});

		playFirstVideo();
	}

	private void playFirstVideo() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				mScrollCalculatorHelper.onScrollStateChanged(mRecyclerView, RecyclerView.SCROLL_STATE_IDLE);
			}
		}, 1000);
	}

	private void initVideoList() {
		// get bundle arguments
		Bundle bundle = getArguments();
		canFresh = bundle.getBoolean("canFresh");
		List<Video> playlist = (List<Video>)bundle.getSerializable("playlist");
		if(playlist == null) {
			Toast.makeText(getContext(), "开始刷新", Toast.LENGTH_SHORT).show();
			mNetManager.execGetFeeds();
		} else {
			mVideoList = playlist;
			mStartPosition = bundle.getInt("startPosition");
		}
	}

	public static VideoFragment launch()
	{
		return launch(null, -1, true);
	}

	public static VideoFragment launch(List<Video> playList, int startPosition, boolean canFresh)
	{
		Bundle bundle = new Bundle();
		bundle.putSerializable("playlist", (Serializable) playList);
		bundle.putInt("startPosition", startPosition);
		bundle.putBoolean("canFresh", canFresh);

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
		mNetManager.cancelAllCalls();
		mHandler.removeCallbacksAndMessages(null);
		if (mInsertVideoRecordsTask != null && !mInsertVideoRecordsTask.isCancelled()) {
			mInsertVideoRecordsTask.cancel(true);
		}
		if (mInsertHistoryRecordTask != null && !mInsertHistoryRecordTask.isCancelled()) {
			mInsertHistoryRecordTask.cancel(true);
		}
	}

}
