package com.example.minidouyin.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Video implements Serializable
{

	@SerializedName("student_id") private String mStudentId;
	@SerializedName("user_name") private String mUserName;
	@SerializedName("image_url") private String mImageUrl;
	@SerializedName("video_url") private String mVideoUrl;

	public Video() {

	}

	public Video(String studentId, String userName, String imageUrl, String videoUrl) {
		mStudentId = studentId;
		mUserName = userName;
		mImageUrl = imageUrl;
		mVideoUrl = videoUrl;
	}

	public String getStudentId() {
		return mStudentId;
	}

	public void setStudentId(String studentId) {
		this.mStudentId = studentId;
	}

	public String getUserName() {
		return mUserName;
	}

	public void setUserName(String userName) {
		this.mUserName = userName;
	}

	public String getImageUrl() {
		return mImageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.mImageUrl = imageUrl;
	}

	public String getVideoUrl() {
		return mVideoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.mVideoUrl = videoUrl;
	}

	@Override
	public String toString()
	{
		return  "student_id" + mStudentId +
				"\nuser_name" + mUserName +
				"\nimage_url" + mImageUrl +
				"\nvideo_url" + mVideoUrl;
	}
}
