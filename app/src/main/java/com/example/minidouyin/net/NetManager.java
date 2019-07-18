package com.example.minidouyin.net;

import com.example.minidouyin.net.response.GetVideosResponse;
import com.example.minidouyin.net.response.PostVideoResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetManager {
	// network
	private Retrofit mRetrofit;
	private IMiniDouyinService mService;

	// listener
	private OnNetListener mGetListener;
	private OnNetListener mPostListener;
	private List<Call> mCallList = new ArrayList<>();

	public NetManager() {
		mRetrofit = new Retrofit.Builder()
				.baseUrl(IMiniDouyinService.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		mService = mRetrofit.create(IMiniDouyinService.class);

	}

	public void setOnGetListener(OnNetListener listener) {
		mGetListener = listener;
	}

	public void setOnPostListener(OnNetListener listener) {
		mPostListener = listener;
	}

	public void execGetFeeds() {
		Call<GetVideosResponse> call = mService.getVideos();
		mCallList.add(call);
		call.enqueue(new Callback<GetVideosResponse>() {
			@Override
			public void onResponse(Call<GetVideosResponse> call, Response<GetVideosResponse> response) {
				mCallList.remove(call);
				mGetListener.exec(response);
			}

			@Override
			public void onFailure(Call<GetVideosResponse> call, Throwable t) {
				mCallList.remove(call);
			}
		});
	}

	public void execPostFeed(String id,
	                         String userName,
	                         MultipartBody.Part image,
	                         MultipartBody.Part video) {
		Call<PostVideoResponse> call = mService.postVideo(id, userName, image, video);
		mCallList.add(call);
		call.enqueue(new Callback<PostVideoResponse>() {
			@Override
			public void onResponse(Call<PostVideoResponse> call, Response<PostVideoResponse> response) {
				mCallList.remove(call);
				mPostListener.exec(response);
			}

			@Override
			public void onFailure(Call<PostVideoResponse> call, Throwable t) {
				mCallList.remove(call);
			}
		});
	}

	public void cancelAllCalls() {
		for (Call call : mCallList) {
			call.cancel();
		}
		mCallList.clear();
	}

}
