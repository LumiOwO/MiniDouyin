package com.example.minidouyin.db;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.example.minidouyin.model.Video;

@Entity(tableName = "videos", primaryKeys = {"id"})
public class VideoRecord {

	@NonNull
	@Embedded
	private Video mVideo;

	@ColumnInfo(name = "hot_value")
	private int mHotValue;

	public VideoRecord(Video video, int hotValue) {
		mVideo = video;
		mHotValue = hotValue;
	}

	public int getHotValue() {
		return mHotValue;
	}

	public void setHotValue(int hotValue) {
		this.mHotValue = hotValue;
	}

	public Video getVideo() {
		return mVideo;
	}

	public void setVideo(Video video) {
		this.mVideo = video;
	}
}
