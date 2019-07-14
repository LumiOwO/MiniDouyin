package com.example.minidouyin.model;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class ScrollViewPager extends ViewPager {

	private boolean mScrollable;

	public boolean getScrollable() {
		return mScrollable;
	}

	public void setScrollable(boolean scrollable) {
		mScrollable = scrollable;
	}

	public ScrollViewPager(@NonNull Context context) {
		super(context);
		mScrollable = false;
	}

	public ScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		mScrollable = false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return mScrollable;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return mScrollable;
	}
}
