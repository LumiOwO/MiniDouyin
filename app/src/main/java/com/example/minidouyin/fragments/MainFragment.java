package com.example.minidouyin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.minidouyin.activities.CameraActivity;
import com.example.minidouyin.R;
import com.example.minidouyin.activities.SearchActivity;
import com.google.android.material.tabs.TabLayout;

public class MainFragment extends Fragment {

	public static final int REQUEST_CODE_FOR_CAMERA = 1001;

	private static final int PAGE_COUNT = 2;

	private ImageButton mPhoto;
	private ImageButton mSearch;
	private ViewPager mViewPager;
	private TabLayout mTabLayout;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);

		mViewPager = view.findViewById(R.id.fragment_main_vp);
		mTabLayout = view.findViewById(R.id.fragment_main_tl);

		mPhoto = view.findViewById(R.id.fragment_main_tv_photo);
		mSearch = view.findViewById(R.id.fragment_main_tv_search);

		mPhoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CameraActivity.launch(getActivity(), REQUEST_CODE_FOR_CAMERA);
			}
		});

		mSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SearchActivity.launch(getActivity());
			}
		});

		mViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
			@Override
			public Fragment getItem(int position) {
				Fragment ret = null;
				if (position == 0) {
					ret = VideoFragment.launch();
				} else if (position == 1) {
					ret = new NearbyFragment();
				}
				return ret;
			}

			@Override
			public int getCount() {
				return PAGE_COUNT;
			}

			@Nullable
			@Override
			public CharSequence getPageTitle(int position) {
				CharSequence ret = null;
				if (position == 0) {
					ret = "推荐";
				} else if (position == 1) {
					ret = "同城";
				}
				return ret;
			}
		});

		mTabLayout.setupWithViewPager(mViewPager);
		return view;
	}

}
