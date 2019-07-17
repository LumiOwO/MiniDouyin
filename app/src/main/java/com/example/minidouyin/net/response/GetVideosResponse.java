package com.example.minidouyin.net.response;

import com.example.minidouyin.model.Video;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetVideosResponse
{
	// "mVideos": [
	// {
	// 		"student_id": "222",
	// 		"user_name": "name",
	// 		"image_url": "...",
	// 		"_id": "...",
	// 		"video_url": "...",
	// 		"createdAt": "...",
	// 		"updatedAt": "...",
	// 		"image_w": 756,
	// 		"image_h": 1008
	// }
	// ...
	// ],
	// "mSuccess": true

	@SerializedName("feeds") 	private List<Video> mVideos = new ArrayList<>();
	@SerializedName("success") 	private boolean mSuccess;


	public List<Video> getVideos() {
		return mVideos;
	}

	public void setVideos(List<Video> videos)
	{
		this.mVideos = videos;
	}


	public boolean isSuccess()
	{
		return mSuccess;
	}

	public void setSuccess(boolean mSuccess)
	{
		this.mSuccess = mSuccess;
	}

	@Override
	public String toString()
	{
		String ret = "";
		ret += "GetVideosResponse{\n" +
				"videos = [\n";
		for (int i = 0; i < mVideos.size(); i++)
			ret += mVideos.get(i).toString() + "\n";
		ret += "], success = " + mSuccess + "}";
		return ret;
	}
}
