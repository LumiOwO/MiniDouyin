package com.example.minidouyin.model;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

	@PrimaryKey
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

	public Date getCreatedAt() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ssz");
		Date date = new Date();
		try {
			date = sdf.parse(mCreatedAt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public Date getUpdatedAt() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ssz");
		Date date = new Date();
		try {
			date = sdf.parse(mUpdatedAt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public int getImageWidth() {
		return Integer.parseInt(mImageWidth);
	}

	public void setImageWidth(int imageWidth) {
		mImageWidth = String.valueOf(imageWidth);
	}

	public void setImageWidth(String imageWidth) {
		mImageWidth = imageWidth;
	}

	public int getImageHeight() {
		return Integer.parseInt(mImageHeight);
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
}
