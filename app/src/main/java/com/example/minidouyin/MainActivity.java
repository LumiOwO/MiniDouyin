package com.example.minidouyin;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

	private static final int PAGE_COUNT = 5;

	private ViewPager mViewPager;
	private AppBarLayout mAppBar;
	private TabLayout mTabLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mViewPager = findViewById(R.id.vp_main);
		mAppBar = findViewById(R.id.ab_main);
		mTabLayout = findViewById(R.id.tl_main);

		mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public Fragment getItem(int position) {
				return new VideoFragment();
			}

			@Override
			public int getCount() {
				return PAGE_COUNT;
			}

			@Nullable
			@Override
			public CharSequence getPageTitle(int position) {
				return super.getPageTitle(position);
			}
		});

		mTabLayout.setupWithViewPager(mViewPager);
	}
}
