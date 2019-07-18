package com.example.minidouyin.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.minidouyin.R;
import com.example.minidouyin.adapter.HotRankAdapter;
import com.example.minidouyin.adapter.MostRankAdapter;
import com.example.minidouyin.db.MiniDouYinDatabaseHelper;
import com.example.minidouyin.db.StudentVideoCountTuple;
import com.example.minidouyin.db.VideoRecord;
import com.example.minidouyin.fragments.SearchFragment;
import com.example.minidouyin.model.Video;

import java.util.List;
import java.util.stream.Collectors;

public class SearchActivity extends AppCompatActivity {

	private static final String TAG = "SearchActivity";

	private ImageButton mBackButton;
	private EditText mSearchText;

	private MostRankAdapter mMostRankAdapter;
	private HotRankAdapter mHotRankAdapter;

	private MiniDouYinDatabaseHelper mDBHelper = new MiniDouYinDatabaseHelper(this);

	private View.OnClickListener mBackToMainListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			finish();
		}
	};
	private View.OnClickListener mPopFragmentListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			mSearchText.clearFocus();
		}
	};

	public static void launch(Activity activity) {
		Intent intent = new Intent(activity, SearchActivity.class);
		activity.startActivity(intent);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		mBackButton = findViewById(R.id.search_ib_back);
		mSearchText = findViewById(R.id.search_et_search);

		// most
		RecyclerView mostRankView = findViewById(R.id.search_most_recyclerView);
		mostRankView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
		mMostRankAdapter = new MostRankAdapter(R.layout.layout_mostrank);
		mostRankView.setAdapter(mMostRankAdapter);

		mMostRankAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				TextView textView = view.findViewById(R.id.most_rank_studentID);
				String studentID = textView.getText().toString();

				mDBHelper.setOnGetVideoByStudentIdListener(new MiniDouYinDatabaseHelper.OnGetVideoByStudentIdListener()
				{
					@Override
					public void run(List<VideoRecord> videoRecords)
					{
						List<Video> videos = videoRecords.stream().map(VideoRecord::getVideo).collect(Collectors.toList());
						PlayActivity.launch(SearchActivity.this, videos, 0);
					}
				});
				mDBHelper.executeGetVideoByStudentId(studentID);

			}
		});
		mMostRankAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
		mMostRankAdapter.isFirstOnly(false);

		mDBHelper.setOnGetVideoCountByOneListener(new MiniDouYinDatabaseHelper.OnGetVideoCountByOneListener() {
			@Override
			public void run(List<StudentVideoCountTuple> lists) {
				Log.d("setOnGetVideoCountByOneListener", lists.size() + "");
				mMostRankAdapter.setNewData(lists);
			}
		});
		mDBHelper.executeGetVideoCountByOne();
		Log.d("setOnGetVideoCountByOneListener", "over");

		// hot
		RecyclerView hotRankView = findViewById(R.id.search_hot_recyclerView);
		hotRankView.setLayoutManager(new LinearLayoutManager(this));
		mHotRankAdapter = new HotRankAdapter(R.layout.layout_hotrank);
		hotRankView.setAdapter(mHotRankAdapter);

		mHotRankAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				PlayActivity.launch(SearchActivity.this, mHotRankAdapter.getData(), position);
			}
		});
		mHotRankAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
		mHotRankAdapter.isFirstOnly(false);

		mDBHelper.setOnGetVideoByHotValueRankListener(new MiniDouYinDatabaseHelper.OnGetVideoByHotValueRankListener()
		{
			@Override
			public void run(List<VideoRecord> videoRecords)
			{
				mHotRankAdapter.updateHotValue(videoRecords);
				mHotRankAdapter.setNewData(videoRecords.stream().map(VideoRecord::getVideo).collect(Collectors.toList()));
			}
		});
		mDBHelper.executeGetVideoByHotValueRank();

		mBackButton.setOnClickListener(mBackToMainListener);

		mSearchText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
					transaction.addToBackStack(null);
					transaction.replace(R.id.search_placeholder, new SearchFragment());
					transaction.commit();
					mBackButton.setOnClickListener(mPopFragmentListener);
					Log.d(TAG, "onFocusChange: gain focus");
				} else {
					getSupportFragmentManager().popBackStack();
					InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
					mBackButton.setOnClickListener(mBackToMainListener);
					Log.d(TAG, "onFocusChange: lose focus");
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mDBHelper.cancelAllAsyncTasks();
	}
}
