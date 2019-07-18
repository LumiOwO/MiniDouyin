package com.example.minidouyin.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.minidouyin.R;
import com.example.minidouyin.fragments.NearbyFragment;
import com.example.minidouyin.fragments.SearchFragment;

public class SearchActivity extends AppCompatActivity {

	private static final String TAG = "SearchActivity";

	private ImageButton mBackButton, mScanButton;
	private EditText mSearchText;

	private View.OnClickListener mBackToMainListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			finish();
		}
	};
	private View.OnClickListener mPopFragmentListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			mSearchText.clearFocus();
		}
	};

	public static void launch(Activity activity) {
		Intent intent = new Intent(activity, SearchActivity.class);
		activity.startActivity(intent);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		mBackButton = findViewById(R.id.search_ib_back);
		mScanButton = findViewById(R.id.search_ib_scan);
		mSearchText = findViewById(R.id.search_et_search);

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(R.id.search_placeholder, new SearchFragment());
		transaction.commit();

		mBackButton.setOnClickListener(mBackToMainListener);

		mSearchText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
					transaction.addToBackStack(null);
					transaction.replace(R.id.search_placeholder, new NearbyFragment());
					transaction.commit();
					mBackButton.setOnClickListener(mPopFragmentListener);
					Log.d(TAG, "onFocusChange: gain focus");
				} else {
					getSupportFragmentManager().popBackStack();
					InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
					mBackButton.setOnClickListener(mBackToMainListener);
					Log.d(TAG, "onFocusChange: lose focus");
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
