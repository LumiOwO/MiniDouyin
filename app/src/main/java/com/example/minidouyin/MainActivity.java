package com.example.minidouyin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.minidouyin.activities.CameraActivity;
import com.example.minidouyin.fragments.InfoFragment;
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
					ret = new InfoFragment();
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
				} else if (position == 1) {
					ret = "我的";
				}
				return ret;
			}
		});

		mTabLayout.setupWithViewPager(mViewPager);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == MainFragment.REQUEST_CODE_FOR_CAMERA) {
			if (resultCode == RESULT_OK) {
				makeToast("上传成功");
			} else if (resultCode == CameraActivity.RESULT_PERMISSION_DENIED) {
				makeToast("权限获取失败");
			} else if (resultCode == CameraActivity.RESULT_ERROR) {
				makeToast("未知错误");
			}
		}
	}

	private void makeToast(String text)
	{
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
}
