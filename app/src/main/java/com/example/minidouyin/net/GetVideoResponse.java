package com.example.minidouyin.net;

import com.example.minidouyin.Video;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetVideoResponse {

	@SerializedName("success") private boolean mSuccess;
	@SerializedName("feeds") private List<Video> mVideoList;

	public List<Video> getVideos() {
		return mVideoList;
	}
}
