package com.example.minidouyin;

import android.app.Application;

import com.example.minidouyin.db.MiniDouYinDatabaseHelper;

public class MainApplication extends Application {

	@Override public void onCreate() {
		super.onCreate();
		MiniDouYinDatabaseHelper.getDatabase(getApplicationContext());
	}
}
