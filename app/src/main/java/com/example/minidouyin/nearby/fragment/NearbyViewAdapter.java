package com.example.minidouyin.nearby.fragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class NearbyViewAdapter extends FragmentPagerAdapter
{
	private static final String TITLE = "同城";

	public NearbyViewAdapter(FragmentManager fm)
	{
		super(fm);
	}

	@Override
	public Fragment getItem(int position)
	{
		return new NearbyFragment();
	}

	@Override
	public int getCount()
	{
		return 1;
	}

	@Nullable
	@Override
	public CharSequence getPageTitle(int position)
	{
		return TITLE;
	}
}
