package com.example.minidouyin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minidouyin.R;
import com.example.minidouyin.fragments.nearby.NearbyVideoAdapter;

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
		mAdapter = new NearbyVideoAdapter();
		recyclerView.setAdapter(mAdapter);

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
