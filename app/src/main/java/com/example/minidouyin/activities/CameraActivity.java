package com.example.minidouyin.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.ErrorListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.example.minidouyin.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraActivity extends AppCompatActivity {

	private static final int CODE_FOR_CAMERA_PERMISSION = 251;
	private static String[] permissions = {
			Manifest.permission.CAMERA,
			Manifest.permission.RECORD_AUDIO,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_SETTINGS,
	};
	private static final String STORAGE_PATH =
			Environment.getExternalStorageDirectory().getPath()
					+ File.separator + "MiniDouyin";

	private JCameraView mCameraView;

	public static void launch(Activity activity, int requestCode) {
		Intent intent = new Intent(activity, CameraActivity.class);
		activity.startActivityForResult(intent, requestCode);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		mCameraView = findViewById(R.id.jcameraview);

		initCamera();

		if (!permissonAllGranted()) {
			ActivityCompat.requestPermissions(
					this, permissions, CODE_FOR_CAMERA_PERMISSION);
		}
	}

	private boolean permissonAllGranted()
	{
		boolean ret = true;
		for(String permission : permissions) {
			ret = ret && ContextCompat.checkSelfPermission(
					getApplication(), permission
			) == PackageManager.PERMISSION_GRANTED;

			if(!ret) break;
		}
		return ret;
	}

	private void initCamera()
	{
		// 设置视频保存路径
		mCameraView.setSaveVideoPath(STORAGE_PATH);

		// 设置只能录像或只能拍照或两种都可以（默认两种都可以）
		mCameraView.setFeatures(JCameraView.BUTTON_STATE_BOTH);

		//设置视频质量
		mCameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_MIDDLE);

		//JCameraView监听
		mCameraView.setErrorLisenter(new ErrorListener() {
			@Override
			public void onError() {
				//打开Camera失败回调
				Log.i("CJT", "open camera error");
			}
			@Override
			public void AudioPermissionError() {
				//没有录取权限回调
				Log.i("CJT", "AudioPermissionError");
			}
		});

		mCameraView.setJCameraLisenter(new JCameraListener() {
			@Override
			public void captureSuccess(Bitmap bitmap) {
				//获取图片bitmap
				Log.i("JCameraView", "bitmap = " + bitmap.getWidth());

				try {
					String timeStamp = new SimpleDateFormat(
							"yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

					File file = new File(STORAGE_PATH,
							File.separator + "IMG_" + timeStamp + ".jpg");
					FileOutputStream fos = new FileOutputStream(file);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
					fos.flush();
					fos.close();

					notifySystemAlbum(Uri.fromFile(file));

				} catch (IOException e) {
					e.printStackTrace();
				}

				finishCameraActivity(RESULT_OK);
			}
			@Override
			public void recordSuccess(String url, Bitmap firstFrame) {
				//获取视频路径
				Log.i("CJT", "url = " + url);

				File file = new File(url);
				notifySystemAlbum(Uri.fromFile(file));

				finishCameraActivity(RESULT_OK);
			}
		});

		//左边按钮点击事件
		mCameraView.setLeftClickListener(new ClickListener() {
			@Override
			public void onClick() {
				finishCameraActivity(RESULT_CANCELED);
			}
		});
		//右边按钮点击事件
		mCameraView.setRightClickListener(new ClickListener() {
			@Override
			public void onClick() {
				makeToast("right button");
			}
		});
	}

	private void finishCameraActivity(int resultCode)
	{
		setResult(resultCode);
		CameraActivity.this.finish();
	}

	private void makeToast(String text)
	{
		Toast.makeText(CameraActivity.this, text, Toast.LENGTH_SHORT).show();
	}

	private void notifySystemAlbum(Uri uri)
	{
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		intent.setData(uri);
		sendBroadcast(intent);
	}
}
