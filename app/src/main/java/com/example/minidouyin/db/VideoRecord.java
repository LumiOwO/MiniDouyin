package com.example.minidouyin.db;


import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.example.minidouyin.model.Video;

@Entity(tableName = "videos")
public class VideoRecord {

	@Embedded
	private Video mVideo;

	@ColumnInfo(name = "hot_value")
	private int mHotValue;


}
