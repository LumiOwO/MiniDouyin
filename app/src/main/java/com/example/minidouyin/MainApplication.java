package com.example.minidouyin;

import android.app.Application;

import com.example.minidouyin.db.MiniDouYinDatabaseHelper;

public class MainApplication extends Application {

	@Override public void onCreate() {
		super.onCreate();
//		LeakSentry.config = LeakSentry.config.copy(watchFragmentViews = false);
		MiniDouYinDatabaseHelper.getDatabase(getApplicationContext());
	}
}
