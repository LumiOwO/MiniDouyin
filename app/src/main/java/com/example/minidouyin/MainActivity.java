package com.example.minidouyin;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.minidouyin.fragments.MainFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

	private static final int PAGE_COUNT = 2;

	private ViewPager mViewPager;
	private TabLayout mTabLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mViewPager = findViewById(R.id.vp_main);
		mTabLayout = findViewById(R.id.tl_main);

		mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public Fragment getItem(int position) {
				Fragment ret = null;
				if (position == 0) {
					ret = new MainFragment();
				} else if (position == 1) {
					ret = new MainFragment();
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
					ret = "首页";
				}
				return ret;
			}
		});

		mTabLayout.setupWithViewPager(mViewPager);

//		new Thread() {
//			@Override
//			public void run() {
//				super.run();
//				while (true) {
//
//				}
//			}
//		}.start();
	}
}
