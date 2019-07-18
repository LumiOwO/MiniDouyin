package com.example.minidouyin.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import com.example.minidouyin.db.VideoRecord;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Random;

public class Video implements Serializable
{
	@ColumnInfo(name = "stuid")
	@SerializedName("student_id")
	private String mStudentId;

	@ColumnInfo(name = "username")
	@SerializedName("user_name")
	private String mUsername;

	@ColumnInfo(name = "image_url")
	@SerializedName("image_url")
	private String mImageUrl;

	@ColumnInfo(name = "video_url")
	@SerializedName("video_url")
	private String mVideoUrl;

	@NonNull
	@ColumnInfo(name = "id")
	@SerializedName("_id")
	private String mId;

	@ColumnInfo(name = "create_time")
	@SerializedName("createdAt")
	private String mCreatedAt;

	@ColumnInfo(name = "update_time")
	@SerializedName("updatedAt")
	private String mUpdatedAt;

	@ColumnInfo(name = "image_width")
	@SerializedName("image_w")
	private String mImageWidth;

	@ColumnInfo(name = "image_height")
	@SerializedName("image_h")
	private String mImageHeight;

	public Video() {

	}

	public Video(String studentId, String userName, String imageUrl, String videoUrl) {
		mStudentId = studentId;
		mUsername = userName;
		mImageUrl = imageUrl;
		mVideoUrl = videoUrl;
	}

	public String getId() {
		return mId;
	}

	public void setId(String id) {
		mId = id;
	}

	public String getStudentId() {
		return mStudentId;
	}

	public void setStudentId(String studentId) {
		this.mStudentId = studentId;
	}

	public String getUsername() {
		return mUsername;
	}

	public void setUsername(String userName) {
		this.mUsername = userName;
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

	public String getCreatedAt() {
		return mCreatedAt;
	}

	public void setCreatedAt(String mCreatedAt) {
		this.mCreatedAt = mCreatedAt;
	}

	public String getUpdatedAt() {
		return mUpdatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		mUpdatedAt = updatedAt;
	}

	public String getImageWidth() {
		return mImageWidth;
	}

	public void setImageWidth(int imageWidth) {
		mImageWidth = String.valueOf(imageWidth);
	}

	public void setImageWidth(String imageWidth) {
		mImageWidth = imageWidth;
	}

	public String getImageHeight() {
		return mImageHeight;
	}

	public void setImageHeight(int imageHeight) {
		mImageHeight = String.valueOf(imageHeight);
	}

	public void setImageHeight(String imageHeight) {
		mImageHeight = imageHeight;
	}

	@Override
	public String toString()
	{
		return "Feed{" +
				"student_id='" + mStudentId + '\'' +
				", user_name='" + mUsername + '\'' +
				", image_url='" + mImageUrl + '\'' +
				", _id='" + mId + '\'' +
				", video_url='" + mVideoUrl + '\'' +
				", createdAt='" + mCreatedAt + '\'' +
				", updatedAt='" + mUpdatedAt + '\'' +
				", image_w=" + mImageWidth +
				", image_h=" + mImageHeight +
				'}';
	}

	private static Random random = new Random(System.currentTimeMillis());

	public VideoRecord createRecord() {
		return new VideoRecord(this, random.nextInt(1000));
	}
}
