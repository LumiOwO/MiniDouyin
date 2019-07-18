package com.example.minidouyin.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VideoDao {

	@Query("select stuid, count(id) as video_count from videos group by stuid order by count(id) desc")
	public List<StudentVideoCountTuple> getVideoCountByOne();

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	public Long insertVideoRecord(VideoRecord videoRecord);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	public List<Long> insertVideoRecords(VideoRecord... videoRecords);

	@Delete
	public int deleteVideo(VideoRecord videoRecord);

	@Update
	public int updateVideo(VideoRecord videoRecord);

}
