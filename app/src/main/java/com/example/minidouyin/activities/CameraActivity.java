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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.ErrorListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.example.minidouyin.R;
import com.example.minidouyin.model.CurrentUser;
import com.example.minidouyin.net.NetManager;
import com.example.minidouyin.net.OnNetListener;
import com.example.minidouyin.net.response.PostVideoResponse;
import com.example.minidouyin.utils.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class CameraActivity extends AppCompatActivity {

	private static final int CODE_FOR_PICK_PHOTO = 999;
	private static final int CODE_FOR_PICK_VIDEO = 998;

	public static final int RESULT_PERMISSION_DENIED = 777;
	public static final int RESULT_ERROR = 404;

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

	enum CameraState {
		PHOTO, VIDEO
	};
	private CameraState mCameraState;
	private Uri mSelectedImage;
	private Uri mSelectedVideo;

	public static void launch(Activity activity, int requestCode) {
		Intent intent = new Intent(activity, CameraActivity.class);
		activity.startActivityForResult(intent, requestCode);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		mCameraView = findViewById(R.id.jcameraview);

		if (!permissonAllGranted()) {
			ActivityCompat.requestPermissions(
					this, permissions, CODE_FOR_CAMERA_PERMISSION);
		} else {
			initCamera();
			toPhotoState();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == CODE_FOR_CAMERA_PERMISSION) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				initCamera();
				toPhotoState();

			} else {
				finishCameraActivity(RESULT_PERMISSION_DENIED);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && data != null) {
			if (requestCode == CODE_FOR_PICK_PHOTO) {
				mSelectedImage = data.getData();
				toVideoState();

			} else if (requestCode == CODE_FOR_PICK_VIDEO) {
				mSelectedVideo = data.getData();
				postVideo();
			}
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
				try {
					String timeStamp = new SimpleDateFormat(
							"yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

					File file = new File(STORAGE_PATH,
							File.separator + "IMG_" + timeStamp + ".jpg");
					FileOutputStream fos = new FileOutputStream(file);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
					fos.flush();
					fos.close();

					mSelectedImage = Uri.fromFile(file);
					notifySystemAlbum(mSelectedImage);

				} catch (IOException e) {
					e.printStackTrace();
				}

				toVideoState();
			}
			@Override
			public void recordSuccess(String url, Bitmap firstFrame) {

				File file = new File(url);
				mSelectedVideo = Uri.fromFile(file);
				notifySystemAlbum(mSelectedVideo);

				postVideo();
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
				if(mCameraState == CameraState.PHOTO) {
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(
							Intent.createChooser(intent, "Select Picture"),
							CODE_FOR_PICK_PHOTO);

				} else if(mCameraState == CameraState.VIDEO) {
					Intent intent = new Intent();
					intent.setType("video/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(
							Intent.createChooser(intent, "Select Video"),
							CODE_FOR_PICK_VIDEO);
				}
			}
		});
	}

	private void finishCameraActivity(int resultCode)
	{
		setResult(resultCode);
		CameraActivity.this.finish();
	}

	private void notifySystemAlbum(Uri uri)
	{
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		intent.setData(uri);
		sendBroadcast(intent);
	}

	private void toPhotoState()
	{
		mCameraState = CameraState.PHOTO;
		mCameraView.setFeatures(JCameraView.BUTTON_STATE_ONLY_CAPTURE);

		makeToast("现在请上传一张封面图片~");
	}

	private void toVideoState()
	{
		mCameraState = CameraState.VIDEO;
		mCameraView.setFeatures(JCameraView.BUTTON_STATE_ONLY_RECORDER);
		mCameraView.onResume();

		makeToast("现在请上传您的视频~");
	}

	private void postVideo()
	{
		if(mSelectedImage == null && mSelectedVideo == null)
			finishCameraActivity(RESULT_ERROR);

		NetManager netManager = new NetManager();
		netManager.setOnPostListener(new OnNetListener()
		{
			@Override
			public void exec(Response<?> res)
			{
				// create videos
				PostVideoResponse response = (PostVideoResponse)res.body();
				if(!response.isSuccess())
					finishCameraActivity(RESULT_ERROR);
				else
					finishCameraActivity(RESULT_OK);
			}
		});

		MultipartBody.Part coverImagePart = getMultipartFromUri("cover_image", mSelectedImage);
		MultipartBody.Part videoPart = getMultipartFromUri("video", mSelectedVideo);
		netManager.execPostFeed(
				CurrentUser.getStudentID(), CurrentUser.getUsername(),
				coverImagePart, videoPart
		);

	}

	private MultipartBody.Part getMultipartFromUri(String name, Uri uri) {
		File f = new File(ResourceUtils.getRealPath(this, uri));
		RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
		return MultipartBody.Part.createFormData(name, f.getName(), requestFile);
	}

	private void makeToast(String text)
	{
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}
}
