package com.example.minidouyin.net;

import com.example.minidouyin.net.response.GetVideosResponse;
import com.example.minidouyin.net.response.PostVideoResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetManager
{
	// network
	private Retrofit mRetrofit;
	private IMiniDouyinService mService;

	// listener
	private OnNetListener mGetListener;
	private OnNetListener mPostListener;

	public NetManager()
	{
		mRetrofit = new Retrofit.Builder()
				.baseUrl(IMiniDouyinService.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		mService = mRetrofit.create(IMiniDouyinService.class);
	}

	public void setOnGetListener(OnNetListener listener)
	{
		mGetListener = listener;
	}

	public void setOnPostListener(OnNetListener listener)
	{
		mPostListener = listener;
	}

	public void execGetFeeds()
	{
		Call<GetVideosResponse> call = mService.getVideos();
		enqueueCall(call, mGetListener);
	}

	public void execPostFeed(String id,
						 String userName,
						 MultipartBody.Part image,
						 MultipartBody.Part video)
	{
		Call<PostVideoResponse> call = mService.postVideo(id, userName, image, video);
		enqueueCall(call, mPostListener);
	}

	private <T> void enqueueCall(Call<T> call, OnNetListener listener)
	{
		call.enqueue(new Callback<T>()
		{
			@Override
			public void onResponse(Call<T> call, Response<T> response)
			{
				listener.exec(response);
			}

			@Override
			public void onFailure(Call<T> call, Throwable t)
			{
				t.printStackTrace();
			}
		});
	}

}
