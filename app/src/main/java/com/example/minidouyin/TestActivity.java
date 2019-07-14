package com.example.minidouyin;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.minidouyin.fragments.nearby.fragment.NearbyViewAdapter;

public class TestActivity extends AppCompatActivity
{
	private ViewPager mViewPager;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		getSupportActionBar().hide();

		mViewPager = findViewById(R.id.nearby_viewPager);
		mViewPager.setAdapter(new NearbyViewAdapter(getSupportFragmentManager()));
	}
}
