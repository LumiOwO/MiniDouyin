package com.example.minidouyin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.minidouyin.R;
import com.example.minidouyin.fragments.nearby.fragment.NearbyFragment;
import com.example.minidouyin.model.ScrollViewPager;
import com.google.android.material.tabs.TabLayout;

public class MainFragment extends Fragment {

	private static final int PAGE_COUNT = 2;

	private ScrollViewPager mViewPager;
	private TabLayout mTabLayout;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);

		mViewPager = view.findViewById(R.id.fragment_main_vp);
		mTabLayout = view.findViewById(R.id.fragment_main_tl);

		mViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
			@Override
			public Fragment getItem(int position) {
				Fragment ret = null;
				if (position == 0) {
					ret = new VideoFragment();
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
