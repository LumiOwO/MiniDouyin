package com.example.minidouyin.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface VideoDao {

	@Insert
	public int insertVideo(VideoRecord videoRecord);

	@Delete
	public int deleteVideo(VideoRecord videoRecord);

	@Update
	public int updateVideo(VideoRecord videoRecord);

}
