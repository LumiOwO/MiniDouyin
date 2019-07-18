package com.example.minidouyin.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.minidouyin.R;
import com.example.minidouyin.activities.PlayActivity;
import com.example.minidouyin.adapter.CollectionRecyclerAdapter;
import com.example.minidouyin.adapter.HistoryRecyclerAdapter;
import com.example.minidouyin.db.CollectionRecord;
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
	private int mCollectionCnt = 0;

	private MiniDouYinDatabaseHelper mDBHelperHistory = new MiniDouYinDatabaseHelper(getContext());
	private MiniDouYinDatabaseHelper mDBHelperCollection = new MiniDouYinDatabaseHelper(getContext());

	private TextView mUsername_title;
	private TextView mUsername_editable;
	private TextView mStudentID_title;
	private TextView mStudentID_editable;
	private Button mUsername_editBtn;
	private Button mStudentID_editBtn;
	private ImageButton mDelete_History_Btn;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_info, container, false);

		mUsername_title = view.findViewById(R.id.username_title);
		mUsername_editable = view.findViewById(R.id.username_editable);
		mStudentID_title = view.findViewById(R.id.studentID_title);
		mStudentID_editable = view.findViewById(R.id.studentID_editable);

		setCurrentUserInfo();

		// set edit button
		mStudentID_editBtn = view.findViewById(R.id.btn_edit_studentID);
		mUsername_editBtn = view.findViewById(R.id.btn_edit_username);
		mDelete_History_Btn = view.findViewById(R.id.btn_delete_history);

		mStudentID_editBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
//				new MaterialDialog.Builder(getActivity())
//					.inputType(InputType.TYPE_CLASS_NUMBER)
//					.input(getString(R.string.duration_hint), durationSelector.getText(), new MaterialDialog.InputCallback() {
//						@Override
//						public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
//							onDurationChanged(input.toString());
//						}
//					})
//					.show();
			}
		});

		mUsername_editBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				setCurrentUserInfo();
			}
		});

		mDelete_History_Btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

			}
		});

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
		final List<Video> collectionVideos = new ArrayList<>();
		mDBHelperCollection.setOnGetVideoByIdListener(new MiniDouYinDatabaseHelper.OnGetVideoByIdListener()
		{
			@Override
			public void run(VideoRecord videoRecord)
			{
				collectionVideos.add(videoRecord.getVideo());
				mCollectionCnt --;
				if(mCollectionCnt == 0)
					mCollection.setNewData(collectionVideos);
			}
		});
		mDBHelperCollection.setOnGetCollectionByStudentIdListener(new MiniDouYinDatabaseHelper.OnGetCollectionByStudentIdListener()
		{
			@Override
			public void run(List<CollectionRecord> collectionRecords)
			{
				mCollectionCnt = collectionRecords.size();
				collectionVideos.clear();
				for(CollectionRecord record : collectionRecords)
				{
					mDBHelperCollection.executeGetVideoById(record.getVideoId());
				}
			}
		});

		// history
		RecyclerView historyView = view.findViewById(R.id.info_history_recyclerView);
		historyView.setLayoutManager(new LinearLayoutManager(getContext()));
		mHistory = new HistoryRecyclerAdapter(R.layout.layout_history);
		historyView.setAdapter(mHistory);

		final List<Video> historyVideos = new ArrayList<>();
		mDBHelperHistory.setOnGetVideoByIdListener(new MiniDouYinDatabaseHelper.OnGetVideoByIdListener()
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
		mDBHelperHistory.setOnGetHistoryByStudentIdListener(new MiniDouYinDatabaseHelper.OnGetHistoryByStudentIdListener()
		{
			@Override
			public void run(List<HistoryRecord> historyRecords)
			{
				mHistoryCnt = historyRecords.size();
				historyVideos.clear();
				for(HistoryRecord record : historyRecords)
				{
					mDBHelperHistory.executeGetVideoById(record.getVideoId());
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
	public void onResume()
	{
		super.onResume();
		refreshData();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		mDBHelperCollection.cancelAllAsyncTasks();
		mDBHelperHistory.cancelAllAsyncTasks();
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser)
	{
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser && isVisible()) {
			refreshData();
		}
	}

	private void setCurrentUserInfo()
	{
		mUsername_title.setText(CurrentUser.getUsername());
		mUsername_editable.setText(CurrentUser.getUsername());
		mStudentID_title.setText(CurrentUser.getStudentID());
		mStudentID_editable.setText(CurrentUser.getStudentID());
	}

	private void refreshData()
	{
		mDBHelperHistory.executeGetHistoryByStudentId(CurrentUser.getStudentID());
		mDBHelperCollection.executeGetCollectionByStudentId(CurrentUser.getStudentID());
	}
}
