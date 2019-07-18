package com.example.minidouyin.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.minidouyin.R;
import com.example.minidouyin.activities.PlayActivity;
import com.example.minidouyin.adapter.CollectionRecyclerAdapter;
import com.example.minidouyin.adapter.HistoryRecyclerAdapter;
import com.example.minidouyin.db.HistoryRecord;
import com.example.minidouyin.db.MiniDouYinDatabaseHelper;
import com.example.minidouyin.db.VideoRecord;
import com.example.minidouyin.model.CurrentUser;
import com.example.minidouyin.model.Video;

import java.util.ArrayList;
import java.util.List;

public class InfoFragment extends Fragment
{
	private CollectionRecyclerAdapter mCollection;
	private HistoryRecyclerAdapter mHistory;

	private int mHistoryCnt = 0;

	private MiniDouYinDatabaseHelper mDBHelper = new MiniDouYinDatabaseHelper(getContext());
	private MiniDouYinDatabaseHelper.GetHistoryRecordByStudentIdTask mHistoryBySidTask;
	private MiniDouYinDatabaseHelper.GetVideoRecordByIdTask mVideoByIDTask;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_info, container, false);

		// collection
		RecyclerView collectionView = view.findViewById(R.id.info_collection_recyclerView);
		collectionView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
		mCollection = new CollectionRecyclerAdapter(R.layout.layout_collection);
		collectionView.setAdapter(mCollection);


		mCollection.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				PlayActivity.launch((Activity) getContext(), mCollection.getData(), position);
			}
		});
		mCollection.openLoadAnimation(BaseQuickAdapter.SCALEIN);
		mCollection.isFirstOnly(false);
		mCollection.setEmptyView(R.layout.layout_nocollection, container);

		// history
		RecyclerView historyView = view.findViewById(R.id.info_history_recyclerView);
		historyView.setLayoutManager(new LinearLayoutManager(getContext()));
		mHistory = new HistoryRecyclerAdapter(R.layout.layout_history);
		historyView.setAdapter(mHistory);

		final List<Video> historyVideos = new ArrayList<>();
		mDBHelper.setOnGetVideoRecordByIdListener(new MiniDouYinDatabaseHelper.OnGetVideoRecordByIdListener()
		{
			@Override
			public void run(VideoRecord videoRecord)
			{
				historyVideos.add(videoRecord.getVideo());
				mHistoryCnt --;
				if(mHistoryCnt == 0)
					mHistory.setNewData(historyVideos);
			}
		});
		mDBHelper.setOnGetHistoryRecordByStudentIdListener(new MiniDouYinDatabaseHelper.OnGetHistoryRecordByStudentIdListener()
		{
			@Override
			public void run(List<HistoryRecord> historyRecords)
			{
				mHistoryCnt = historyRecords.size();
				historyVideos.clear();
				for(HistoryRecord record : historyRecords)
				{
					mVideoByIDTask = mDBHelper.executeGetVideoRecordById(record.getVideoId());
				}
			}
		});

		mHistory.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				PlayActivity.launch((Activity) getContext(), mHistory.getData(), position);
			}
		});
		mHistory.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
		mHistory.isFirstOnly(false);
		mHistory.setEmptyView(R.layout.layout_nohistory, container);

		return view;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser)
	{
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser && isVisible()) {
			mHistoryBySidTask = mDBHelper.executeGetHistoryRecordByStudentId(CurrentUser.getStudentID());
		}
	}
}
