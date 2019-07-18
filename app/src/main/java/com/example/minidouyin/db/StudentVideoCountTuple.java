package com.example.minidouyin.db;

import androidx.room.ColumnInfo;

public class StudentVideoCountTuple {

	@ColumnInfo(name = "stuid")
	private String mStudentId;

	@ColumnInfo(name = "video_count")
	private int mVideoCount;

	public int getVideoCount() {
		return mVideoCount;
	}

	public void setStudentId(String studentId) {
		this.mStudentId = studentId;
	}

	public String getStudentId() {
		return mStudentId;
	}

	public void setVideoCount(int videoCount) {
		this.mVideoCount = videoCount;
	}
}
