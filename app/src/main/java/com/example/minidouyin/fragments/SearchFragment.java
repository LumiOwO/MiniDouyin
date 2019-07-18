package com.example.minidouyin.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.minidouyin.R;
import com.example.minidouyin.db.StudentVideoCountTuple;
import com.example.minidouyin.db.MiniDouYinDatabaseHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends Fragment {

	private static final String TAG = "SearchFragment";

	@BindView(R.id.tv_text)
	TextView mText;

	// database
	private MiniDouYinDatabaseHelper mDatabaseHelper = new MiniDouYinDatabaseHelper(getContext());

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search, container, false);

		ButterKnife.bind(this, view);

		mDatabaseHelper.setOnGetVideoCountByOneListener(new MiniDouYinDatabaseHelper.OnGetVideoCountByOneListener() {
			@Override
			public void run(List<StudentVideoCountTuple> lists) {
				int total = 0;
				StringBuilder str = new StringBuilder();
				for (StudentVideoCountTuple student : lists) {
					total += student.getVideoCount();
					str.append(student.getStudentId()).append(" ").append(student.getVideoCount() + "").append("\n");
				}
				mText.setText(str.toString());
				Log.e(TAG, "run: " + total);
			}
		});

		mDatabaseHelper.executeGetVideoCountByOne();

		return view;
	}

}
