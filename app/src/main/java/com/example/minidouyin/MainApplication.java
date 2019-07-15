package com.example.minidouyin;

import android.app.Application;

public class MainApplication extends Application {

	@Override public void onCreate() {
		super.onCreate();
//		LeakSentry.config = LeakSentry.config.copy(watchFragmentViews = false);
	}
}
