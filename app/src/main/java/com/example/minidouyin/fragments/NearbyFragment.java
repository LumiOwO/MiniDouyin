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
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.minidouyin.R;
import com.example.minidouyin.activities.PlayActivity;
import com.example.minidouyin.adapter.NearbyVideoAdapter;
import com.example.minidouyin.model.Video;

public class NearbyFragment extends Fragment
{
	// recycler view
	private NearbyVideoAdapter mAdapter;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_nearby, container, false);

		// get recycler view
		RecyclerView recyclerView = view.findViewById(R.id.nearby_recyclerView);
		// show in two columns
		recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
		// bind adapter
		mAdapter = new NearbyVideoAdapter(R.layout.videopreview_nearby);
		recyclerView.setAdapter(mAdapter);

		mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				PlayActivity.launch((Activity) getContext(), mAdapter.getData(), position);
			}
		});
		mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
		mAdapter.isFirstOnly(false);

		return view;
	}

	@Override
	public void onStart()
	{
		super.onStart();
		// notify adapter to update info
		mAdapter.refreshView();
	}
}
