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
import com.example.minidouyin.adapter.NearbyVideoAdapter;
import com.example.minidouyin.model.Video;

import java.util.ArrayList;

public class InfoFragment extends Fragment
{
	private CollectionRecyclerAdapter mCollection;
	private RecyclerView mHistory;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_info, container, false);
		RecyclerView collectionView = view.findViewById(R.id.info_collection_recyclerView);
		mHistory = view.findViewById(R.id.info_history_recyclerView);

		// show in two columns
		collectionView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
		mCollection = new CollectionRecyclerAdapter(R.layout.layout_collection);
		collectionView.setAdapter(mCollection);
		mCollection.setNewData(new ArrayList<Video>() {
			{
//				for(int i=0; i<10; i++)
//				add(new Video("2", "1", "https://sf1-hscdn-tos.pstatp.com/obj/developer-baas/baas/tt7217xbo2wz3cem41/fe588f07ebf686f9_1563458992883.jpg",
//						"https://sf3-hscdn-tos.pstatp.com/obj/developer-baas/baas/tt7217xbo2wz3cem41/64a4af811b75a2fe_1563458992992.mp4"));
			}
		});

		mCollection.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				PlayActivity.launch((Activity) getContext(), mCollection.getData(), position);
			}
		});
		mCollection.openLoadAnimation(BaseQuickAdapter.SCALEIN);
		mCollection.isFirstOnly(false);
		mCollection.setEmptyView(R.layout.layout_nocollection, container);

		return view;
	}


}
