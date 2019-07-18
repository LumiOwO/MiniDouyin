package com.example.minidouyin.db;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "collection", primaryKeys = {"stuid", "video_id"})
public class CollectionRecord {

	@NonNull
	@ColumnInfo(name = "stuid")
	private String mStudentId;

	@NonNull
	@ColumnInfo(name = "video_id")
	private String mVideoId;

	public CollectionRecord(String studentId, String videoId) {
		mStudentId = studentId;
		mVideoId = videoId;
	}

	public String getStudentId() {
		return mStudentId;
	}

	public void setStudentId(String studentId) {
		this.mStudentId = studentId;
	}

	public String getVideoId() {
		return mVideoId;
	}

	public void setVideoId(String videoId) {
		this.mVideoId = videoId;
	}

}
