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

	@Query("select * from videos where id=:videoId")
	VideoRecord getVideoById(String videoId);

	@Query("select * from videos where stuid=:studentId")
	List<VideoRecord> getVideoByStudentId(String studentId);

	@Query("select stuid, count(id) as video_count from videos group by stuid order by count(id) desc limit :limit")
	List<StudentVideoCountTuple> getVideoCountByOne(int limit);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	List<Long> insertVideoRecord(VideoRecord... videoRecords);

	@Delete
	int deleteVideo(VideoRecord videoRecord);

	@Update
	int updateVideo(VideoRecord videoRecord);

	@Query("select * from videos order by hot_value desc limit :limit")
	List<VideoRecord> getVideoByHotValueRank(int limit);

	@Query("update videos set hot_value=hot_value+1 where id=:videoId")
	int hotValueIncrement(String videoId);

}
